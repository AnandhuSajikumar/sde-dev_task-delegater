import React, { useState } from 'react';

const EngineerForm = ({ onSubmit, initialData = {}, onCancel }) => {
    const [name, setName] = useState(initialData.name || '');
    const [techStack, setTechStack] = useState(initialData.techStack || '');
    const [gender, setGender] = useState(initialData.gender || 'MALE');

    const isEdit = !!initialData.id;

    const handleSubmit = (e) => {
        e.preventDefault();
        const data = {
            name,
            techStack,
            ...(isEdit ? {} : { gender })
        };
        onSubmit(data);
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            <div>
                <label className="block mb-1 text-secondary">Name</label>
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    placeholder="e.g. Anandhu Sajikumar"
                    required
                />
            </div>

            <div>
                <label className="block mb-1 text-secondary">Tech Stack</label>
                <input
                    type="text"
                    value={techStack}
                    onChange={(e) => setTechStack(e.target.value)}
                    placeholder="e.g. Java, Spring Boot, React"
                    required
                />
                <small className="text-secondary">Comma separated values</small>
            </div>

            {!isEdit && (
                <div>
                    <label className="block mb-1 text-secondary">Gender</label>
                    <select value={gender} onChange={(e) => setGender(e.target.value)}>
                        <option value="MALE">Male</option>
                        <option value="FEMALE">Female</option>
                        <option value="OTHER">Other</option>
                    </select>
                </div>
            )}

            <div className="flex justify-end gap-2 mt-4">
                <button type="button" className="btn btn-secondary" onClick={onCancel}>
                    Cancel
                </button>
                <button type="submit" className="btn btn-primary">
                    {isEdit ? 'Update' : 'Create'}
                </button>
            </div>
        </form>
    );
};

export default EngineerForm;
