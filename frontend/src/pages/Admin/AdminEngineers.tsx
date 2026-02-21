import { useEffect, useState } from 'react';
import api from '../../api/axios';
import { Button } from '../../components/ui/Button';
import { Trash2, ExternalLink } from 'lucide-react';

interface Engineer {
    id: number;
    name: string;
    email: string;
    techStack: string;
    gender: string;
}

export const AdminEngineers = () => {
    const [engineers, setEngineers] = useState<Engineer[]>([]);
    const [loading, setLoading] = useState(true);

    const fetchEngineers = async () => {
        try {
            setLoading(true);
            const response = await api.get('/engineers/all');
            // Response.data.content is the list
            if (response.data.content) {
                setEngineers(response.data.content);
            }
        } catch (error) {
            console.error("Failed to fetch engineers", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchEngineers();
    }, []);

    const handleDelete = async (id: number) => {
        if (!window.confirm('Are you sure you want to delete this engineer?')) return;
        try {
            await api.delete(`/engineers/${id}`);
            setEngineers(engineers.filter(e => e.id !== id));
        } catch (error) {
            console.error("Failed to delete engineer", error);
            alert("Failed to delete engineer");
        }
    };

    return (
        <div>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
                <h1 style={{ fontSize: '1.875rem', fontWeight: 'bold' }}>Engineers</h1>
                <Button onClick={fetchEngineers} variant="outline">Refresh</Button>
            </div>

            {loading ? (
                <p>Loading...</p>
            ) : (
                <div className="card" style={{ padding: 0, overflow: 'hidden' }}>
                    <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
                        <thead style={{ backgroundColor: 'hsl(210, 20%, 98%)', borderBottom: '1px solid var(--color-border)' }}>
                            <tr>
                                <th style={{ padding: '1rem', fontWeight: 600, fontSize: '0.875rem', color: 'var(--color-text-secondary)' }}>Name</th>
                                <th style={{ padding: '1rem', fontWeight: 600, fontSize: '0.875rem', color: 'var(--color-text-secondary)' }}>Tech Stack</th>
                                <th style={{ padding: '1rem', fontWeight: 600, fontSize: '0.875rem', color: 'var(--color-text-secondary)' }}>Gender</th>
                                <th style={{ padding: '1rem', fontWeight: 600, fontSize: '0.875rem', color: 'var(--color-text-secondary)', textAlign: 'right' }}>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {engineers.map((engineer) => (
                                <tr key={engineer.id} style={{ borderBottom: '1px solid var(--color-border)' }}>
                                    <td style={{ padding: '1rem', fontWeight: 500 }}>{engineer.name}</td>
                                    <td style={{ padding: '1rem' }}>
                                        <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
                                            {engineer.techStack && engineer.techStack.split(',').map((tech) => (
                                                <span key={tech.trim()} className="badge badge-blue">{tech.trim()}</span>
                                            ))}
                                        </div>
                                    </td>
                                    <td style={{ padding: '1rem' }}>{engineer.gender}</td>
                                    <td style={{ padding: '1rem', textAlign: 'right' }}>
                                        <button
                                            onClick={() => handleDelete(engineer.id)}
                                            style={{ color: 'var(--color-status-danger)', background: 'none', border: 'none', cursor: 'pointer', padding: '0.5rem' }}
                                            title="Delete Engineer"
                                        >
                                            <Trash2 size={18} />
                                        </button>
                                    </td>
                                </tr>
                            ))}
                            {engineers.length === 0 && (
                                <tr>
                                    <td colSpan={4} style={{ padding: '2rem', textAlign: 'center', color: 'var(--color-text-secondary)' }}>
                                        No engineers found.
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
};
