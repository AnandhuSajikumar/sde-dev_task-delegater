import { useEffect, useState } from 'react';
import api from '../../api/axios';
import { Button } from '../../components/ui/Button';

interface UserData {
    firstname: string;
    lastname: string;
    email: string;
    role: string;
}

export const AdminUsers = () => {
    const [users, setUsers] = useState<UserData[]>([]);
    const [loading, setLoading] = useState(true);

    const fetchUsers = async () => {
        try {
            setLoading(true);
            const response = await api.get('/auth/users');
            setUsers(response.data);
        } catch (error) {
            console.error("Failed to fetch users", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchUsers();
    }, []);

    const handlePromote = async (email: string) => {
        if (!window.confirm(`Are you sure you want to promote ${email} to Admin?`)) return;
        try {
            await api.put(`/auth/promote/${email}`);
            alert(`${email} has been promoted to Admin successfully!`);
            fetchUsers(); // Refresh the list
        } catch (error: any) {
            console.error("Failed to promote user", error);
            alert(`Failed to promote: ${error.response?.data?.message || error.message}`);
        }
    };

    if (loading) return <div style={{ textAlign: 'center', padding: '3rem' }}>Loading users...</div>;

    return (
        <div>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-end', marginBottom: '2rem' }}>
                <div>
                    <h1 style={{ fontSize: '1.875rem', fontWeight: 'bold' }}>User Management</h1>
                    <p style={{ color: 'var(--color-text-secondary)', marginTop: '0.25rem' }}>View registered users and manage roles.</p>
                </div>
            </div>

            <div className="card" style={{ padding: 0, overflow: 'hidden' }}>
                <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
                    <thead style={{ backgroundColor: 'var(--color-bg-app)', borderBottom: '1px solid var(--color-border)' }}>
                        <tr>
                            <th style={{ padding: '1rem 1.5rem', fontWeight: 600, fontSize: '0.875rem', color: 'var(--color-text-secondary)' }}>Name</th>
                            <th style={{ padding: '1rem 1.5rem', fontWeight: 600, fontSize: '0.875rem', color: 'var(--color-text-secondary)' }}>Email</th>
                            <th style={{ padding: '1rem 1.5rem', fontWeight: 600, fontSize: '0.875rem', color: 'var(--color-text-secondary)' }}>Role</th>
                            <th style={{ padding: '1rem 1.5rem', fontWeight: 600, fontSize: '0.875rem', color: 'var(--color-text-secondary)', textAlign: 'right' }}>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {users.map((user) => (
                            <tr key={user.email} style={{ borderBottom: '1px solid var(--color-border)' }}>
                                <td style={{ padding: '1rem 1.5rem', fontSize: '0.95rem' }}>
                                    {user.firstname} {user.lastname}
                                </td>
                                <td style={{ padding: '1rem 1.5rem', color: 'var(--color-text-secondary)', fontSize: '0.9rem' }}>
                                    {user.email}
                                </td>
                                <td style={{ padding: '1rem 1.5rem' }}>
                                    <span className={`badge ${user.role === 'ADMIN' ? 'badge-blue' : 'badge-gray'}`} style={{ fontSize: '0.75rem' }}>
                                        {user.role}
                                    </span>
                                </td>
                                <td style={{ padding: '1rem 1.5rem', textAlign: 'right' }}>
                                    {user.role !== 'ADMIN' && (
                                        <Button
                                            variant="outline"
                                            style={{ padding: '0.375rem 0.75rem', fontSize: '0.875rem' }}
                                            onClick={() => handlePromote(user.email)}
                                        >
                                            Make Admin
                                        </Button>
                                    )}
                                </td>
                            </tr>
                        ))}
                        {users.length === 0 && (
                            <tr>
                                <td colSpan={4} style={{ padding: '2rem', textAlign: 'center', color: 'var(--color-text-secondary)' }}>
                                    No users found.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};
