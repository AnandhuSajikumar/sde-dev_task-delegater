import React, { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        // Check local storage for persisted auth
        const storedUser = localStorage.getItem('taskflow_user');
        if (storedUser) {
            setUser(JSON.parse(storedUser));
            setIsAuthenticated(true);
        }
    }, []);

    const login = (email, password) => {
        // Mock login - accept any credentials for now
        // In a real app, this would make an API call
        const mockUser = {
            id: '1',
            name: 'Demo User',
            email: email,
            role: 'Product Manager',
            avatar: 'https://ui-avatars.com/api/?name=Demo+User&background=0052CC&color=fff'
        };

        setUser(mockUser);
        setIsAuthenticated(true);
        localStorage.setItem('taskflow_user', JSON.stringify(mockUser));
        return true;
    };

    const logout = () => {
        setUser(null);
        setIsAuthenticated(false);
        localStorage.removeItem('taskflow_user');
    };

    return (
        <AuthContext.Provider value={{ user, isAuthenticated, login, logout }}>
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
