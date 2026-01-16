import React from 'react';

const EngineerCard = ({ engineer, onEdit, onDelete }) => {
    return (
        <div className="card" style={{ display: 'flex', alignItems: 'center', gap: '1rem', padding: '1rem' }}>
            <div style={{
                width: '48px',
                height: '48px',
                borderRadius: '50%',
                backgroundColor: 'var(--bg-input)',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                color: 'var(--primary)',
                fontWeight: 'bold',
                fontSize: '1.25rem'
            }}>
                {engineer.name.charAt(0).toUpperCase()}
            </div>

            <div style={{ flex: 1 }}>
                <h3 style={{ margin: '0 0 0.25rem 0', fontSize: '1rem' }}>{engineer.name}</h3>
                <p style={{ margin: 0, fontSize: '0.875rem', color: 'var(--text-secondary)' }}>
                    {engineer.role}
                </p>
                <div style={{ marginTop: '0.25rem' }}>
                    <span className={`badge ${engineer.status === 'BUSY' ? 'badge-in_progress' : 'badge-done'}`} style={{ fontSize: '10px' }}>
                        {engineer.status}
                    </span>
                </div>
            </div>

            <div className="flex flex-col gap-2">
                <button className="btn btn-secondary" style={{ padding: '4px 8px', fontSize: '12px' }} onClick={() => onEdit(engineer)}>
                    Edit
                </button>
                <button className="btn btn-danger" style={{ padding: '4px 8px', fontSize: '12px' }} onClick={() => onDelete(engineer)}>
                    Delete
                </button>
            </div>
        </div>
    );
};

export default EngineerCard;
