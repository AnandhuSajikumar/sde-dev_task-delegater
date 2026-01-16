import React, { useEffect } from 'react';

const Modal = ({ isOpen, onClose, title, children }) => {
    if (!isOpen) return null;

    useEffect(() => {
        const handleEsc = (e) => {
            if (e.key === 'Escape') onClose();
        };
        window.addEventListener('keydown', handleEsc);
        return () => window.removeEventListener('keydown', handleEsc);
    }, [onClose]);

    return (
        <div style={{
            position: 'fixed', top: 0, left: 0, right: 0, bottom: 0,
            backgroundColor: 'rgba(9, 30, 66, 0.54)', // Jira overlay color
            display: 'flex', alignItems: 'center', justifyContent: 'center',
            zIndex: 1000
        }} onClick={onClose}>
            <div className="card" style={{ width: '100%', maxWidth: '500px', transform: 'none', backgroundColor: 'var(--bg-surface)', boxShadow: 'var(--shadow-md)', border: 'none' }} onClick={e => e.stopPropagation()}>
                <div className="flex justify-between items-center mb-6">
                    <h3 style={{ margin: 0, fontSize: '1.25rem' }}>{title}</h3>
                    <button className="btn btn-secondary" style={{ padding: '0.25rem 0.5rem', minWidth: 'auto' }} onClick={onClose}>
                        &times;
                    </button>
                </div>
                <div>
                    {children}
                </div>
            </div>
        </div>
    );
};

export default Modal;
