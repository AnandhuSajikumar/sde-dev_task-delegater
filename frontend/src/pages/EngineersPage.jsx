import React, { useEffect, useState } from 'react';
import { getAllEngineers, createEngineer, updateEngineer, deleteEngineer } from '../services/api';
import EngineerCard from '../components/EngineerCard';
import Modal from '../components/Modal';
import EngineerForm from '../components/EngineerForm';

const EngineersPage = () => {
    const [engineers, setEngineers] = useState([]);
    const [loading, setLoading] = useState(true);

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [editingEngineer, setEditingEngineer] = useState(null);

    const fetchEngineers = async () => {
        setLoading(true);
        try {
            const res = await getAllEngineers({ size: 100 });
            setEngineers(res.data.content);
        } catch (err) {
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchEngineers();
    }, []);

    const handleCreate = async (data) => {
        try {
            await createEngineer(data);
            setIsModalOpen(false);
            fetchEngineers();
        } catch (err) {
            alert('Failed to create engineer');
        }
    };

    const handleUpdate = async (data) => {
        try {
            await updateEngineer(editingEngineer.id, data);
            setIsModalOpen(false);
            setEditingEngineer(null);
            fetchEngineers();
        } catch (err) {
            alert('Failed to update engineer');
        }
    };

    const handleDelete = async (engineer) => {
        if (!window.confirm(`Delete ${engineer.name}?`)) return;
        try {
            await deleteEngineer(engineer.id);
            fetchEngineers();
        } catch (err) {
            alert('Failed to delete engineer. Ensure they have no assigned tasks.');
        }
    };

    const openCreateModal = () => {
        setEditingEngineer(null);
        setIsModalOpen(true);
    };

    const openEditModal = (engineer) => {
        setEditingEngineer(engineer);
        setIsModalOpen(true);
    };

    return (
        <div className="container">
            <div className="flex items-center justify-between mt-8 mb-6">
                <h1>Engineers</h1>
                <button className="btn btn-primary" onClick={openCreateModal}>
                    + Add Engineer
                </button>
            </div>

            {loading ? (
                <div className="text-center py-8">Loading...</div>
            ) : engineers.length === 0 ? (
                <div className="text-center py-8 text-secondary">
                    No engineers found.
                </div>
            ) : (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4">
                    {engineers.map(eng => (
                        <EngineerCard
                            key={eng.id}
                            engineer={eng}
                            onEdit={openEditModal}
                            onDelete={handleDelete}
                        />
                    ))}
                </div>
            )}

            <Modal
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
                title={editingEngineer ? 'Edit Engineer' : 'New Engineer'}
            >
                <EngineerForm
                    initialData={editingEngineer || {}}
                    onSubmit={editingEngineer ? handleUpdate : handleCreate}
                    onCancel={() => setIsModalOpen(false)}
                />
            </Modal>
        </div>
    );
};

export default EngineersPage;
