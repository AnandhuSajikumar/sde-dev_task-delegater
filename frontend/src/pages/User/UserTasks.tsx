import { useEffect, useState } from 'react';
import api from '../../api/axios';
import { CheckCircle } from 'lucide-react';

interface Task {
    id: number;
    title: string;
    taskStatus: string;
}

export const UserTasks = () => {
    const [tasks, setTasks] = useState<Task[]>([]);
    const [loading, setLoading] = useState(true);

    const fetchTasks = async () => {
        try {
            setLoading(true);
            const response = await api.get('/engineers/me/tasks');
            if (response.data.content) {
                setTasks(response.data.content);
            }
        } catch (error) {
            console.error("Failed to fetch tasks", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchTasks();
    }, []);

    const handleComplete = async (taskId: number) => {
        try {
            await api.post(`/tasks/${taskId}/complete`);
            fetchTasks(); // Refresh
        } catch (error) {
            console.error("Failed to complete task", error);
        }
    };

    return (
        <div>
            <h1 style={{ fontSize: '1.875rem', fontWeight: 'bold', marginBottom: '2rem' }}>My Tasks</h1>

            {loading ? <p>Loading...</p> : (
                <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: '1.5rem' }}>
                    {tasks.map((task) => (
                        <div key={task.id} className="card" style={{ display: 'flex', flexDirection: 'column', gap: '1rem', borderTop: `4px solid ${task.taskStatus === 'COMPLETED' ? 'var(--color-status-success)' : 'var(--color-status-warning)'}` }}>
                            <div style={{ flex: 1 }}>
                                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'start', marginBottom: '0.5rem' }}>
                                    <h3 style={{ fontSize: '1.25rem', fontWeight: 600 }}>{task.title}</h3>
                                    <span className={`badge ${task.taskStatus === 'COMPLETED' ? 'badge-success' : 'badge-warning'}`}>
                                        {task.taskStatus}
                                    </span>
                                </div>
                                <p style={{ color: 'var(--color-text-secondary)', fontSize: '0.875rem' }}>
                                    Task ID: #{task.id}
                                </p>
                            </div>

                            {task.taskStatus !== 'COMPLETED' && (
                                <button
                                    className="btn btn-primary"
                                    style={{ width: '100%', justifyContent: 'center' }}
                                    onClick={() => handleComplete(task.id)}
                                >
                                    <CheckCircle size={18} />
                                    Mark as Complete
                                </button>
                            )}
                            {task.taskStatus === 'COMPLETED' && (
                                <div style={{ textAlign: 'center', color: 'var(--color-status-success)', fontWeight: 500, padding: '0.5rem' }}>
                                    Completed
                                </div>
                            )}
                        </div>
                    ))}
                    {tasks.length === 0 && (
                        <div className="card" style={{ gridColumn: '1 / -1', textAlign: 'center', padding: '3rem', color: 'var(--color-text-secondary)' }}>
                            No tasks assigned yet.
                        </div>
                    )}
                </div>
            )}
        </div>
    );
};
