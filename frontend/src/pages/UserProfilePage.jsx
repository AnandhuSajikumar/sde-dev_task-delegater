import React from 'react';
import { useAuth } from '../context/AuthContext';

const UserProfilePage = () => {
    const { user, logout } = useAuth();

    if (!user) return <div>Loading...</div>;

    return (
        <div className="container" style={{ maxWidth: '800px', paddingTop: '2rem' }}>
            <h1 className="mb-4">My Profile</h1>

            <div className="card">
                <div className="flex items-center gap-4" style={{ marginBottom: '2rem' }}>
                    <img
                        src={user.avatar}
                        alt={user.name}
                        style={{
                            width: '80px',
                            height: '80px',
                            borderRadius: '50%',
                            border: '2px solid var(--border-color)'
                        }}
                    />
                    <div>
                        <h2 style={{ marginBottom: '0.25rem' }}>{user.name}</h2>
                        <p style={{ margin: 0, color: 'var(--text-secondary)' }}>{user.email}</p>
                        <span className="badge" style={{
                            backgroundColor: 'var(--info-bg)',
                            color: 'var(--info)',
                            marginTop: '0.5rem',
                            display: 'inline-block'
                        }}>
                            {user.role}
                        </span>
                    </div>
                </div>

                <div style={{ borderTop: '1px solid var(--border-color)', paddingTop: '2rem' }}>
                    <h3 style={{ fontSize: '1rem', textTransform: 'uppercase', color: 'var(--text-secondary)', marginBottom: '1rem' }}>
                        Account Settings
                    </h3>

                    <div className="grid grid-cols-1 gap-4">
                        <div className="flex justify-between items-center" style={{ padding: '1rem', backgroundColor: 'var(--bg-input)', borderRadius: 'var(--radius)' }}>
                            <div>
                                <strong style={{ display: 'block' }}>Email Preferences</strong>
                                <small>Manage your email notification settings</small>
                            </div>
                            <button className="btn btn-secondary">Edit</button>
                        </div>

                        <div className="flex justify-between items-center" style={{ padding: '1rem', backgroundColor: 'var(--bg-input)', borderRadius: 'var(--radius)' }}>
                            <div>
                                <strong style={{ display: 'block' }}>Password</strong>
                                <small>Change your password</small>
                            </div>
                            <button className="btn btn-secondary">Change</button>
                        </div>
                    </div>
                </div>

                <div style={{ marginTop: '2rem', textAlign: 'right' }}>
                    <button className="btn btn-danger" onClick={logout}>
                        Log Out
                    </button>
                </div>
            </div>
        </div>
    );
};

export default UserProfilePage;
