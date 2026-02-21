import { useEffect, useState } from 'react';
import type { KeyboardEvent } from 'react';
import api from '../../api/axios';
import { Button } from '../../components/ui/Button';
import { Input } from '../../components/ui/Input';

interface EngineerProfile {
    name: string;
    techStack: string;
    gender: string;
    age?: number;
}

export const UserProfile = () => {
    const [profile, setProfile] = useState<EngineerProfile | null>(null);
    const [loading, setLoading] = useState(true);
    const [isEditing, setIsEditing] = useState(false);

    // Form State (techStack is maintained as an array in local state for convenience)
    const [formData, setFormData] = useState({
        name: '',
        age: 0,
        gender: '',
        techStack: [] as string[]
    });

    const [techInput, setTechInput] = useState('');

    const fetchProfile = async () => {
        try {
            setLoading(true);
            const response = await api.get('/engineers/me');
            setProfile(response.data);
            setFormData({
                name: response.data.name,
                age: 0,
                gender: '',
                techStack: response.data.techStack ? response.data.techStack.split(',').map((t: string) => t.trim()).filter(Boolean) : []
            });
        } catch (error: any) {
            if (error.response && error.response.status === 404) {
                setProfile(null);
            } else {
                console.error("Failed to fetch profile", error);
            }
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchProfile();
    }, []);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const payload = {
            ...formData,
            gender: formData.gender.toUpperCase(),
            techStack: formData.techStack.join(', ') // Send as a single string
        };

        try {
            if (profile) {
                await api.patch('/engineers/update/0', payload);
            } else {
                await api.post('/engineers/me', payload);
            }
            setIsEditing(false);
            fetchProfile();
        } catch (error: any) {
            console.error("Failed to save profile", error);
            alert(`Failed to save profile: ${error.response?.data?.message || 'Unknown error'}`);
        }
    };

    const handleAddTech = (e: KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Enter' || e.key === ',') {
            e.preventDefault();
            const value = techInput.trim().replace(/,/g, '');
            if (value && !formData.techStack.includes(value)) {
                setFormData(prev => ({ ...prev, techStack: [...prev.techStack, value] }));
            }
            setTechInput('');
        } else if (e.key === 'Backspace' && techInput === '' && formData.techStack.length > 0) {
            e.preventDefault();
            const newTechStack = [...formData.techStack];
            const popped = newTechStack.pop();
            setFormData(prev => ({ ...prev, techStack: newTechStack }));
            if (popped) setTechInput(popped);
        }
    };

    const handleRemoveTech = (techToRemove: string) => {
        setFormData(prev => ({ ...prev, techStack: prev.techStack.filter(t => t !== techToRemove) }));
    };

    if (loading) return <div style={{ textAlign: 'center', padding: '3rem' }}>Loading...</div>;

    if (!profile && !isEditing) {
        return (
            <div className="card" style={{ textAlign: 'center', padding: '3rem' }}>
                <h2 style={{ fontSize: '1.5rem', marginBottom: '1rem' }}>No Profile Found</h2>
                <p style={{ color: 'var(--color-text-secondary)', marginBottom: '1.5rem' }}>You haven't created your engineer profile yet.</p>
                <Button onClick={() => setIsEditing(true)}>Create Profile</Button>
            </div>
        );
    }

    if (isEditing) {
        return (
            <div className="card" style={{ maxWidth: '600px', margin: '0 auto' }}>
                <h2 style={{ fontSize: '1.5rem', marginBottom: '1.5rem' }}>{profile ? 'Edit Profile' : 'Create Profile'}</h2>
                <form onSubmit={handleSubmit}>
                    <Input
                        label="Review Name"
                        value={formData.name}
                        onChange={e => setFormData({ ...formData, name: e.target.value })}
                        required
                    />
                    <div style={{ display: 'flex', gap: '1rem', marginTop: '1rem' }}>
                        <Input
                            label="Age"
                            type="number"
                            value={formData.age}
                            onChange={e => setFormData({ ...formData, age: Number(e.target.value) })}
                            required
                        />
                        <div className="flex flex-col gap-1 mb-3" style={{ flex: 1 }}>
                            <label className="text-sm font-medium mb-1 block" style={{ color: 'var(--color-text-secondary)' }}>Gender</label>
                            <select
                                className="input-field"
                                value={formData.gender}
                                onChange={e => setFormData({ ...formData, gender: e.target.value })}
                                required
                            >
                                <option value="">Select...</option>
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                                <option value="Other">Other</option>
                            </select>
                        </div>
                    </div>
                    <div style={{ marginTop: '1rem' }}>
                        <label className="text-sm font-medium mb-1 block" style={{ color: 'var(--color-text-secondary)' }}>Tech Stack</label>
                        <div
                            className="input-field"
                            style={{
                                display: 'flex',
                                flexWrap: 'wrap',
                                gap: '0.5rem',
                                minHeight: '42px',
                                padding: '0.375rem 0.75rem',
                                alignItems: 'center'
                            }}
                            onClick={() => document.getElementById('tech-input')?.focus()}
                        >
                            {formData.techStack.map(tech => (
                                <span key={tech} className="badge badge-blue" style={{ display: 'flex', alignItems: 'center', gap: '0.25rem', padding: '0.25rem 0.5rem' }}>
                                    {tech}
                                    <button
                                        type="button"
                                        onClick={() => handleRemoveTech(tech)}
                                        style={{ background: 'none', border: 'none', color: 'inherit', cursor: 'pointer', padding: 0, font: 'inherit', display: 'flex', alignItems: 'center', opacity: 0.7 }}
                                    >
                                        &times;
                                    </button>
                                </span>
                            ))}
                            <input
                                id="tech-input"
                                type="text"
                                value={techInput}
                                onChange={e => setTechInput(e.target.value)}
                                onKeyDown={handleAddTech}
                                placeholder={formData.techStack.length === 0 ? "Type a skill and press Enter..." : (formData.techStack.length < 3 ? "Add more..." : "")}
                                style={{ flex: 1, border: 'none', outline: 'none', minWidth: '120px', background: 'transparent', color: 'var(--color-text-main)', fontSize: '0.875rem' }}
                            />
                        </div>
                        <p style={{ fontSize: '0.75rem', color: 'var(--color-text-secondary)', marginTop: '0.25rem' }}>Press Enter or comma to add a skill</p>
                    </div>
                    <div style={{ display: 'flex', gap: '1rem', marginTop: '2rem', justifyContent: 'flex-end' }}>
                        <Button type="button" variant="outline" onClick={() => setIsEditing(false)}>Cancel</Button>
                        <Button type="submit">Save Profile</Button>
                    </div>
                </form>
            </div>
        );
    }

    return (
        <div>
            <h1 style={{ fontSize: '1.875rem', fontWeight: 'bold', marginBottom: '2rem' }}>My Profile</h1>
            <div className="card" style={{ maxWidth: '600px' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '1.5rem' }}>
                    <div>
                        <h2 style={{ fontSize: '1.5rem', fontWeight: 600 }}>{profile?.name}</h2>
                        <p style={{ color: 'var(--color-text-secondary)', marginTop: '0.25rem' }}>Engineer</p>
                    </div>
                    <Button variant="outline" onClick={() => setIsEditing(true)}>Edit</Button>
                </div>

                <div>
                    <h3 style={{ fontSize: '1rem', fontWeight: 600, marginBottom: '0.75rem' }}>Tech Stack</h3>
                    <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
                        {profile?.techStack?.split(',').map(tech => (
                            <span key={tech} className="badge badge-blue">{tech.trim()}</span>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
};
