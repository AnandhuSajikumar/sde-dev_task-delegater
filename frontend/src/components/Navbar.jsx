import { NavLink, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Navbar = () => {
    const { user } = useAuth();

    return (
        <nav className="navbar">
            <div className="container flex items-center justify-between">
                <div className="flex items-center gap-4">
                    <div className="flex items-center gap-2">
                        <div style={{ width: '24px', height: '24px', backgroundColor: 'var(--primary)', borderRadius: '3px' }}></div>
                        <span style={{ fontSize: '1.25rem', fontWeight: 600, color: 'var(--text-primary)' }}>
                            TaskFlow
                        </span>
                    </div>

                    <div className="flex items-center gap-2" style={{ marginLeft: '2rem' }}>
                        <NavLink to="/" className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`} style={{
                            borderBottom: '2px solid transparent',
                            borderRadius: '0',
                            padding: '1rem 0.5rem'
                        }}>
                            Dashboard
                        </NavLink>
                        <NavLink to="/tasks" className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`} style={{
                            borderBottom: '2px solid transparent',
                            borderRadius: '0',
                            padding: '1rem 0.5rem'
                        }}>
                            Tasks
                        </NavLink>
                        <NavLink to="/engineers" className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`} style={{
                            borderBottom: '2px solid transparent',
                            borderRadius: '0',
                            padding: '1rem 0.5rem'
                        }}>
                            People
                        </NavLink>
                    </div>
                </div>

                <div className="flex items-center gap-2">
                    {user && (
                        <Link to="/profile" className="flex items-center gap-2" style={{ textDecoration: 'none', color: 'var(--text-primary)' }}>
                            <img
                                src={user.avatar}
                                alt={user.name}
                                style={{
                                    width: '32px',
                                    height: '32px',
                                    borderRadius: '50%',
                                    border: '1px solid var(--border-color)'
                                }}
                            />
                        </Link>
                    )}
                </div>
            </div>
        </nav>
    );
};

export default Navbar;
