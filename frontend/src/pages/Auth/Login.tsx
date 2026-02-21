import { useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import { Button } from '../../components/ui/Button';
import { Input } from '../../components/ui/Input';
import api from '../../api/axios';
import { useNavigate, Link } from 'react-router-dom';

export const Login = () => {
    const { login } = useAuth();
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        setError('');

        try {
            const response = await api.post('/auth/login', { email, password });
            // Response is { token: "..." } based on AuthController
            if (response.data.token) {
                login(response.data.token);
                // Redirect will be handled by logic or explicit navigation
                // Since login sets state, we might need to wait or just navigate.
                // AuthContext doesn't auto-redirect on login, only logout.
                // So we check role and navigate.
                // But we don't have role immediately if we just set token? 
                // Context updates are async.
                // Actually, login() is synchronous in setting localStorage, but state update is async.

                // We can parse token here or just wait for effect.
                // Simple way: Navigation to /dashboard which redirects based on role.
                navigate('/dashboard');
            }
        } catch (err: any) {
            console.error(err);
            setError('Invalid credentials');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div style={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <div className="card" style={{ width: '100%', maxWidth: '420px', textAlign: 'center' }}>
                <h2 style={{ fontSize: '1.75rem', marginBottom: '0.5rem', color: 'var(--color-text-main)' }}>Sign In</h2>
                <p style={{ color: 'var(--color-text-secondary)', marginBottom: '2rem', fontSize: '0.95rem' }}>Welcome back to Nexus</p>

                {error && <div style={{ backgroundColor: 'rgba(239, 68, 68, 0.1)', color: '#ef4444', padding: '0.875rem', borderRadius: 'var(--radius-md)', marginBottom: '1.5rem', fontSize: '0.9rem', border: '1px solid rgba(239,68,68,0.2)' }}>{error}</div>}

                <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1.25rem', textAlign: 'left' }}>
                    <div>
                        <Input
                            label="Email Address"
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                            placeholder="name@company.com"
                        />
                    </div>
                    <div>
                        <Input
                            label="Password"
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                            placeholder="••••••••"
                        />
                    </div>
                    <Button type="submit" className="btn-primary" style={{ width: '100%', marginTop: '0.5rem', padding: '0.875rem' }} isLoading={isLoading}>
                        Sign In
                    </Button>
                </form>

                <p style={{ marginTop: '2rem', fontSize: '0.9rem', color: 'var(--color-text-secondary)' }}>
                    Don't have an account? <Link to="/register" style={{ color: 'var(--color-primary)', fontWeight: 600, textDecoration: 'underline', textUnderlineOffset: '4px' }}>Sign up</Link>
                </p>
            </div>
        </div>
    );
};
