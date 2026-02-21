import { useState } from 'react';
import { Button } from '../../components/ui/Button';
import { Input } from '../../components/ui/Input';
import api from '../../api/axios';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

export const Register = () => {
    const { login } = useAuth();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        firstname: '',
        lastname: '',
        email: '',
        password: '',
        role: 'USER' // Default to USER, can be changed to ADMIN
    });
    const [error, setError] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        setError('');

        try {
            const response = await api.post('/auth/register', formData);
            if (response.data.token) {
                login(response.data.token);
                navigate('/dashboard');
            } else {
                navigate('/login');
            }
        } catch (err: any) {
            console.error(err);
            setError('Registration failed. Email might be taken.');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div style={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <div className="card" style={{ width: '100%', maxWidth: '440px', textAlign: 'center' }}>
                <h2 style={{ fontSize: '1.75rem', marginBottom: '0.5rem', color: 'var(--color-text-main)' }}>Create Account</h2>
                <p style={{ color: 'var(--color-text-secondary)', marginBottom: '2rem', fontSize: '0.95rem' }}>Join the Nexus platform</p>

                {error && <div style={{ backgroundColor: 'rgba(239, 68, 68, 0.1)', color: '#ef4444', padding: '0.875rem', borderRadius: 'var(--radius-md)', marginBottom: '1.5rem', fontSize: '0.9rem', border: '1px solid rgba(239,68,68,0.2)' }}>{error}</div>}

                <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1.25rem', textAlign: 'left' }}>
                    <div style={{ display: 'flex', gap: '1rem' }}>
                        <div style={{ flex: 1 }}>
                            <Input
                                label="First Name"
                                name="firstname"
                                value={formData.firstname}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div style={{ flex: 1 }}>
                            <Input
                                label="Last Name"
                                name="lastname"
                                value={formData.lastname}
                                onChange={handleChange}
                                required
                            />
                        </div>
                    </div>

                    <div>
                        <Input
                            label="Email Address"
                            type="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div>
                        <Input
                            label="Password"
                            type="password"
                            name="password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <Button type="submit" className="btn-primary" style={{ width: '100%', marginTop: '0.5rem', padding: '0.875rem' }} isLoading={isLoading}>
                        Sign Up
                    </Button>
                </form>

                <p style={{ marginTop: '2rem', fontSize: '0.9rem', color: 'var(--color-text-secondary)' }}>
                    Already have an account? <Link to="/login" style={{ color: 'var(--color-primary)', fontWeight: 600, textDecoration: 'underline', textUnderlineOffset: '4px' }}>Sign in</Link>
                </p>
            </div>
        </div>
    );
};
