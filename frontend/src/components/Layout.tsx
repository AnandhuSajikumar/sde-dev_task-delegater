import { ReactNode } from 'react';
import { Sidebar } from './Sidebar';

export const Layout = ({ children }: { children: ReactNode }) => {
    return (
        <div style={{ display: 'flex', minHeight: '100vh', backgroundColor: 'var(--color-bg-app)' }}>
            <Sidebar />
            <main style={{
                flex: 1,
                marginLeft: '240px',
                padding: '2rem',
                overflowY: 'auto'
            }}>
                <div className="container">
                    {children}
                </div>
            </main>
        </div>
    );
};
