export const AdminOverview = () => {
    return (
        <div>
            <h1 style={{ fontSize: '1.875rem', fontWeight: 'bold', marginBottom: '1.5rem', color: 'var(--color-text-main)' }}>Dashboard Overview</h1>
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))', gap: '1.5rem' }}>
                <div className="card">
                    <h3 style={{ fontSize: '1rem', color: 'var(--color-text-secondary)', marginBottom: '0.5rem' }}>Total Engineers</h3>
                    <p style={{ fontSize: '2rem', fontWeight: 'bold', color: 'var(--color-primary)' }}>--</p>
                </div>
                <div className="card">
                    <h3 style={{ fontSize: '1rem', color: 'var(--color-text-secondary)', marginBottom: '0.5rem' }}>Active Tasks</h3>
                    <p style={{ fontSize: '2rem', fontWeight: 'bold', color: 'var(--color-primary)' }}>--</p>
                </div>
                <div className="card">
                    <h3 style={{ fontSize: '1rem', color: 'var(--color-text-secondary)', marginBottom: '0.5rem' }}>Pending Assignment</h3>
                    <p style={{ fontSize: '2rem', fontWeight: 'bold', color: 'var(--color-status-warning)' }}>--</p>
                </div>
            </div>
            <p style={{ marginTop: '2rem', color: 'var(--color-text-secondary)' }}>
                Welcome to the Admin Dashboard. Manage engineers and tasks from the sidebar.
            </p>
        </div>
    );
};
