import { Link } from 'react-router-dom';
import { Button } from '../components/ui/Button';

export const Home = () => {
    return (
        <div style={{ minHeight: '100vh', display: 'flex', flexDirection: 'column' }}>
            {/* Minimalist Top Nav */}
            <header style={{ padding: '1.5rem 2rem', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div style={{ fontFamily: 'var(--font-sans)', fontWeight: 600, fontSize: '1.25rem', letterSpacing: '-0.02em', color: '#1d1d1f' }}>
                    nexus<span style={{ color: 'var(--color-text-secondary)' }}>.dev</span>
                </div>

                <nav style={{ display: 'flex', gap: '2rem', fontSize: '0.85rem', fontWeight: 500, color: 'var(--color-text-secondary)' }}>
                    <a href="#" style={{ letterSpacing: '0.05em' }}>PLATFORM</a>
                    <a href="#" style={{ letterSpacing: '0.05em' }}>DEVELOPERS</a>
                    <a href="#" style={{ letterSpacing: '0.05em' }}>COMPANY</a>
                </nav>

                <div style={{ display: 'flex', gap: '0.75rem' }}>
                    <Link to="/login">
                        <Button variant="primary">Experience Nexus</Button>
                    </Link>
                    <Link to="/register">
                        <Button variant="outline">Talk to Sales</Button>
                    </Link>
                </div>
            </header>

            {/* Hero Section */}
            <main style={{ flex: 1, display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', paddingBottom: '10vh' }}>
                <div className="badge" style={{ marginBottom: '2.5rem', boxShadow: '0 4px 14px 0 rgba(0,0,0,0.05)', color: '#3b82f6', border: '1px solid rgba(255,255,255,0.8)' }}>
                    India's Sovereign Software Platform
                </div>

                <h1 style={{
                    fontFamily: 'var(--font-serif)',
                    fontSize: '4.5rem',
                    fontWeight: 400,
                    color: '#1d1d1f',
                    marginBottom: '1.5rem',
                    textAlign: 'center',
                    lineHeight: 1.1,
                    letterSpacing: '-0.03em'
                }}>
                    Engineering for all<br />from India
                </h1>

                <p style={{
                    color: '#4b5563',
                    fontSize: '1.15rem',
                    textAlign: 'center',
                    maxWidth: '600px',
                    lineHeight: 1.6,
                    fontWeight: 400,
                    marginBottom: '3rem'
                }}>
                    Built on sovereign infrastructure. Powered by frontier-class tools.<br />
                    Delivering population-scale impact.
                </p>

                <Link to="/register">
                    <Button variant="primary" style={{ padding: '1rem 2.5rem', fontSize: '1rem' }}>
                        Join Nexus
                    </Button>
                </Link>
            </main>

            <footer style={{ padding: '2rem', textAlign: 'center', fontSize: '0.75rem', letterSpacing: '0.1em', fontWeight: 600, color: 'var(--color-text-secondary)', textTransform: 'uppercase' }}>
                INDIA BUILDS WITH NEXUS
            </footer>
        </div>
    );
};
