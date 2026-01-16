import React, { useState } from 'react';

const TaskForm = ({ onSubmit, initialData = {}, onCancel }) => {
    const [title, setTitle] = useState(initialData.title || '');

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit({ title });
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            <div>
                <label style={{ display: 'block', marginBottom: '0.5rem', color: 'var(--text-secondary)' }}>
                    Task Title
                </label>
                <input
                    type="text"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    placeholder="e.g. Fix login bug"
                    required
                    autoFocus
                />
            </div>

            <div className="flex justify-end gap-2 mt-4">
                <button type="button" className="btn btn-secondary" onClick={onCancel}>
                    Cancel
                </button>
                <button type="submit" className="btn btn-primary">
                    {initialData.id ? 'Update' : 'Create'}
                </button>
            </div>
        </form>
    );
};

export default TaskForm;
