import { Link, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { LayoutDashboard, Users, CheckSquare, LogOut, UserCircle } from 'lucide-react';

export const Sidebar = () => {
    const { role, logout } = useAuth();
    const location = useLocation();

    const isActive = (path: string) => location.pathname.startsWith(path);

    // Common Links

    // Admin Links
    const adminLinks = [
        { path: '/admin', label: 'Overview', icon: LayoutDashboard },
        { path: '/admin/engineers', label: 'Engineers', icon: Users },
        { path: '/admin/users', label: 'System Users', icon: Users },
        { path: '/admin/tasks', label: 'Tasks', icon: CheckSquare },
    ];

    // User Links
    const userLinks = [
        { path: '/user', label: 'My Tasks', icon: CheckSquare },
        { path: '/user/profile', label: 'My Profile', icon: UserCircle },
    ];

    const links = role === 'ROLE_ADMIN' ? adminLinks : userLinks;

    return (
        <aside style={{
            width: '240px',
            backgroundColor: 'var(--color-bg-surface)',
            borderRight: '1px solid var(--color-border)',
            height: '100vh',
            position: 'fixed',
            left: 0,
            top: 0,
            display: 'flex',
            flexDirection: 'column',
            zIndex: 10
        }}>
            <div style={{ padding: '1.5rem', borderBottom: '1px solid var(--color-border)' }}>
                <h1 style={{ fontSize: '1.25rem', fontWeight: 'bold', color: 'var(--color-primary)', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                    <LayoutDashboard size={24} />
                    SDE Dev
                </h1>
            </div>

            <nav style={{ flex: 1, padding: '1rem' }}>
                <ul style={{ listStyle: 'none', display: 'flex', flexDirection: 'column', gap: '0.5rem' }}>
                    {links.map((link) => (
                        <li key={link.path}>
                            <Link
                                to={link.path}
                                style={{
                                    display: 'flex',
                                    alignItems: 'center',
                                    gap: '0.75rem',
                                    padding: '0.75rem 1rem',
                                    borderRadius: 'var(--radius-md)',
                                    color: isActive(link.path) ? 'var(--color-primary)' : 'var(--color-text-secondary)',
                                    backgroundColor: isActive(link.path) ? 'hsl(216, 100%, 96%)' : 'transparent',
                                    fontWeight: isActive(link.path) ? 600 : 500,
                                    transition: 'all 0.2s'
                                }}
                            >
                                <link.icon size={20} />
                                {link.label}
                            </Link>
                        </li>
                    ))}
                </ul>
            </nav>

            <div style={{ padding: '1rem', borderTop: '1px solid var(--color-border)' }}>
                <button
                    onClick={logout}
                    style={{
                        width: '100%',
                        display: 'flex',
                        alignItems: 'center',
                        gap: '0.75rem',
                        padding: '0.75rem 1rem',
                        borderRadius: 'var(--radius-md)',
                        color: 'var(--color-status-danger)',
                        background: 'none',
                        border: 'none',
                        cursor: 'pointer',
                        fontWeight: 500,
                        transition: 'background 0.2s'
                    }}
                    onMouseEnter={(e) => e.currentTarget.style.backgroundColor = 'hsl(350, 100%, 98%)'}
                    onMouseLeave={(e) => e.currentTarget.style.backgroundColor = 'transparent'}
                >
                    <LogOut size={20} />
                    Sign Out
                </button>
            </div>
        </aside>
    );
};
