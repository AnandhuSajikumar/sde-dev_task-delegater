import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const LoginPage = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const { login } = useAuth();
    const navigate = useNavigate();
    const [error, setError] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        setError('');

        if (!email || !password) {
            setError('Please enter both email and password.');
            return;
        }

        const success = login(email, password);
        if (success) {
            navigate('/');
        } else {
            setError('Invalid credentials.');
        }
    };

    return (
        <div style={{
            minHeight: '100vh',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            backgroundColor: 'var(--bg-body)'
        }}>
            <div style={{ marginBottom: '2rem', textAlign: 'center' }}>
                <h1 style={{
                    color: 'var(--primary)',
                    fontSize: '2rem',
                    marginBottom: '0.5rem',
                    letterSpacing: '-0.03em'
                }}>
                    TaskFlow
                </h1>
                <p style={{ margin: 0 }}>Project Management for Pros</p>
            </div>

            <div className="card" style={{ width: '100%', maxWidth: '400px', padding: '2.5rem' }}>
                <h2 style={{ textAlign: 'center', marginBottom: '2rem', color: '#5E6C84' }}>Log in to your account</h2>

                {error && (
                    <div className="badge badge-danger" style={{
                        display: 'block',
                        padding: '1rem',
                        marginBottom: '1.5rem',
                        backgroundColor: 'var(--danger-bg)',
                        color: 'var(--danger)',
                        textAlign: 'center',
                        borderRadius: '3px'
                    }}>
                        {error}
                    </div>
                )}

                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label style={{
                            display: 'block',
                            marginBottom: '0.5rem',
                            fontSize: '0.875rem',
                            fontWeight: 600,
                            color: '#5E6C84'
                        }}>
                            Email Address
                        </label>
                        <input
                            type="email"
                            placeholder="Enter your email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            style={{ width: '100%' }}
                        />
                    </div>

                    <div className="mb-4">
                        <label style={{
                            display: 'block',
                            marginBottom: '0.5rem',
                            fontSize: '0.875rem',
                            fontWeight: 600,
                            color: '#5E6C84'
                        }}>
                            Password
                        </label>
                        <input
                            type="password"
                            placeholder="Enter your password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            style={{ width: '100%' }}
                        />
                    </div>

                    <button type="submit" className="btn btn-primary" style={{
                        width: '100%',
                        padding: '0.75rem',
                        fontSize: '1rem',
                        marginTop: '1rem'
                    }}>
                        Log In
                    </button>
                </form>

                <div style={{ marginTop: '2rem', textAlign: 'center', borderTop: '1px solid var(--border-color)', paddingTop: '1.5rem' }}>
                    <small>
                        <a href="#" style={{ textDecoration: 'none', color: 'var(--primary)' }}>Can't log in?</a>
                        <span style={{ margin: '0 0.5rem' }}>•</span>
                        <a href="#" style={{ textDecoration: 'none', color: 'var(--primary)' }}>Sign up for an account</a>
                    </small>
                </div>
            </div>

            <div style={{ marginTop: '2rem', color: '#6B778C', fontSize: '0.875rem' }}>
                &copy; 2024 TaskFlow Inc.
            </div>
        </div>
    );
};

export default LoginPage;
