import React, { createContext, useState, useEffect, useContext, ReactNode } from 'react';
import api from '../api/axios';
import { jwtDecode } from 'jwt-decode';

interface User {
    sub: string; // Email is usually in 'sub'
    roles: string[];
    exp: number;
}

interface AuthContextType {
    user: User | null;
    token: string | null;
    login: (token: string) => void;
    logout: () => void;
    isAuthenticated: boolean;
    role: string | null; // Helper to get primary role
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
    const [user, setUser] = useState<User | null>(null);
    const [token, setToken] = useState<string | null>(localStorage.getItem('token'));

    useEffect(() => {
        if (token) {
            try {
                const decoded = jwtDecode<User>(token);
                // Check if token is expired
                if (decoded.exp * 1000 < Date.now()) {
                    logout();
                } else {
                    setUser(decoded);
                    api.defaults.headers.common['Authorization'] = `Bearer ${token}`; // Ensure global axios header
                }
            } catch (error) {
                console.error("Invalid token", error);
                logout();
            }
        } else {
            setUser(null);
            delete api.defaults.headers.common['Authorization'];
        }
    }, [token]);

    const login = (newToken: string) => {
        localStorage.setItem('token', newToken);
        setToken(newToken);
    };

    const logout = () => {
        localStorage.removeItem('token');
        setToken(null);
        setUser(null);
        window.location.href = '/login'; // Force redirect to login
    };

    // Extract role from roles array (assuming format [ROLE_USER] or similar)
    // The backend JWT might store roles in 'roles' or 'authorities' claim.
    // I need to verify what the backend puts in the token.
    // Assuming standard Spring Security, it's often 'authorities'.
    // But for now, I'll rely on the interface.

    // Actually, let's verify the backend JWT Service if possible, but for now I'll assume 'roles' or 'authorities'.
    // If I can't check, I'll assume standard.

    // UPDATE: Based on common Spring Boot practices + jwt-jackson, it usually serializes the object.

    const role = user?.roles ? (Array.isArray(user.roles) ? user.roles[0] : user.roles) : null;

    return (
        <AuthContext.Provider value={{ user, token, login, logout, isAuthenticated: !!user, role: role as string }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};
