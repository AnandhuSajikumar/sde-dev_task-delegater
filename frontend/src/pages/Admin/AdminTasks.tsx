import { useEffect, useState } from 'react';
import api from '../../api/axios';
import { Button } from '../../components/ui/Button';
import { Input } from '../../components/ui/Input';
import { Trash2, Edit2, UserPlus, UserMinus, Plus } from 'lucide-react';

interface Task {
    id: number;
    title: string;
    taskStatus: string;
    engineerId: number | null;
}

interface Engineer {
    id: number;
    name: string;
}

export const AdminTasks = () => {
    const [tasks, setTasks] = useState<Task[]>([]);
    const [engineers, setEngineers] = useState<Engineer[]>([]);
    const [loading, setLoading] = useState(true);

    // Modal States
    const [isEditOpen, setIsEditOpen] = useState(false);
    const [currentTask, setCurrentTask] = useState<Task | null>(null);
    const [newTaskTitle, setNewTaskTitle] = useState('');

    const [isAssignOpen, setIsAssignOpen] = useState(false);
    const [selectedEngineerId, setSelectedEngineerId] = useState<number | ''>('');

    const fetchTasks = async () => {
        try {
            const response = await api.get('/tasks');
            if (response.data.content) {
                setTasks(response.data.content);
            }
        } catch (error) {
            console.error("Failed to fetch tasks", error);
        }
    };

    const fetchEngineers = async () => {
        try {
            const response = await api.get('/engineers/all');
            if (response.data.content) {
                setEngineers(response.data.content);
            }
        } catch (error) {
            console.error("Failed to fetch engineers", error);
        }
    };

    useEffect(() => {
        setLoading(true);
        Promise.all([fetchTasks(), fetchEngineers()]).finally(() => setLoading(false));
    }, []);

    const handleDelete = async (id: number) => {
        if (!window.confirm('Delete task?')) return;
        try {
            await api.delete(`/tasks/${id}`);
            fetchTasks(); // Refresh
        } catch (error) {
            console.error(error);
        }
    };

    const handleCreateOrUpdate = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            if (currentTask) {
                await api.put(`/tasks/${currentTask.id}`, { title: newTaskTitle });
            } else {
                await api.post('/tasks', { title: newTaskTitle });
            }
            setIsEditOpen(false);
            setNewTaskTitle('');
            setCurrentTask(null);
            fetchTasks();
        } catch (error: any) {
            console.error("Failed to save task", error);
            alert(`Failed to save task: ${error.response?.data?.message || error.message}`);
        }
    };

    const openCreateModal = () => {
        setCurrentTask(null);
        setNewTaskTitle('');
        setIsEditOpen(true);
    };

    const openEditModal = (task: Task) => {
        setCurrentTask(task);
        setNewTaskTitle(task.title);
        setIsEditOpen(true);
    };

    const openAssignModal = (task: Task) => {
        setCurrentTask(task);
        setSelectedEngineerId(task.engineerId || ''); // Preselect if existing? 
        // Actually api doesn't support re-assign directly without unassign?
        // TaskController: assignTaskToEnigneer(taskId, engineerId)
        // TaskController: unAssignTask(taskId)
        setIsAssignOpen(true);
    };

    const handleAssign = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!currentTask || !selectedEngineerId) return;
        try {
            await api.put(`/tasks/${currentTask.id}/assign/${selectedEngineerId}`);
            setIsAssignOpen(false);
            fetchTasks();
        } catch (error) {
            console.error(error);
        }
    };

    const handleUnassign = async (task: Task) => {
        if (!window.confirm(`Unassign task "${task.title}"?`)) return;
        try {
            await api.put(`/tasks/${task.id}/unassign`);
            fetchTasks();
        } catch (error) {
            console.error(error);
        }
    };

    const getEngineerName = (id: number | null) => {
        if (!id) return 'Unassigned';
        const eng = engineers.find(e => e.id === id);
        return eng ? eng.name : 'Unknown';
    };

    return (
        <div>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
                <h1 style={{ fontSize: '1.875rem', fontWeight: 'bold' }}>Tasks</h1>
                <Button onClick={openCreateModal}>
                    <Plus size={18} />
                    Create Task
                </Button>
            </div>

            {loading ? <p>Loading...</p> : (
                <div className="card" style={{ padding: 0, overflow: 'hidden' }}>
                    <table style={{ width: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
                        <thead style={{ backgroundColor: 'hsl(210, 20%, 98%)', borderBottom: '1px solid var(--color-border)' }}>
                            <tr>
                                <th style={{ padding: '1rem', color: 'var(--color-text-secondary)', fontSize: '0.875rem' }}>Title</th>
                                <th style={{ padding: '1rem', color: 'var(--color-text-secondary)', fontSize: '0.875rem' }}>Status</th>
                                <th style={{ padding: '1rem', color: 'var(--color-text-secondary)', fontSize: '0.875rem' }}>Assigned To</th>
                                <th style={{ padding: '1rem', color: 'var(--color-text-secondary)', fontSize: '0.875rem', textAlign: 'right' }}>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {tasks.map((task) => (
                                <tr key={task.id} style={{ borderBottom: '1px solid var(--color-border)' }}>
                                    <td style={{ padding: '1rem', fontWeight: 500 }}>{task.title}</td>
                                    <td style={{ padding: '1rem' }}>
                                        <span className={`badge ${task.taskStatus === 'COMPLETED' ? 'badge-success' : 'badge-warning'}`}>
                                            {task.taskStatus}
                                        </span>
                                    </td>
                                    <td style={{ padding: '1rem' }}>
                                        <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                                            {getEngineerName(task.engineerId)}
                                            {task.engineerId ? (
                                                <button onClick={() => handleUnassign(task)} title="Unassign" style={{ border: 'none', background: 'none', cursor: 'pointer', color: 'var(--color-status-danger)' }}>
                                                    <UserMinus size={16} />
                                                </button>
                                            ) : (
                                                <button onClick={() => openAssignModal(task)} title="Assign" style={{ border: 'none', background: 'none', cursor: 'pointer', color: 'var(--color-primary)' }}>
                                                    <UserPlus size={16} />
                                                </button>
                                            )}
                                        </div>
                                    </td>
                                    <td style={{ padding: '1rem', textAlign: 'right' }}>
                                        <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '0.5rem' }}>
                                            <button onClick={() => openEditModal(task)} style={{ border: 'none', background: 'none', cursor: 'pointer', color: 'var(--color-text-secondary)' }}>
                                                <Edit2 size={18} />
                                            </button>
                                            <button onClick={() => handleDelete(task.id)} style={{ border: 'none', background: 'none', cursor: 'pointer', color: 'var(--color-status-danger)' }}>
                                                <Trash2 size={18} />
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            ))}
                            {tasks.length === 0 && (
                                <tr>
                                    <td colSpan={4} style={{ padding: '2rem', textAlign: 'center', color: 'var(--color-text-secondary)' }}>
                                        No tasks found.
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            )}

            {/* Edit/Create Modal */}
            {isEditOpen && (
                <div style={{ position: 'fixed', inset: 0, backgroundColor: 'rgba(0,0,0,0.5)', display: 'flex', alignItems: 'center', justifyContent: 'center', zIndex: 50 }}>
                    <div className="card" style={{ width: '100%', maxWidth: '400px' }}>
                        <h2 style={{ fontSize: '1.25rem', fontWeight: 'bold', marginBottom: '1rem' }}>{currentTask ? 'Edit Task' : 'New Task'}</h2>
                        <form onSubmit={handleCreateOrUpdate}>
                            <Input
                                label="Title"
                                value={newTaskTitle}
                                onChange={(e) => setNewTaskTitle(e.target.value)}
                                required
                            />
                            <div style={{ display: 'flex', gap: '1rem', marginTop: '1.5rem', justifyContent: 'flex-end' }}>
                                <Button type="button" variant="outline" onClick={() => setIsEditOpen(false)}>Cancel</Button>
                                <Button type="submit">Save</Button>
                            </div>
                        </form>
                    </div>
                </div>
            )}

            {/* Assign Modal */}
            {isAssignOpen && (
                <div style={{ position: 'fixed', inset: 0, backgroundColor: 'rgba(0,0,0,0.5)', display: 'flex', alignItems: 'center', justifyContent: 'center', zIndex: 50 }}>
                    <div className="card" style={{ width: '100%', maxWidth: '400px' }}>
                        <h2 style={{ fontSize: '1.25rem', fontWeight: 'bold', marginBottom: '1rem' }}>Assign Task</h2>
                        <form onSubmit={handleAssign}>
                            <div className="flex flex-col gap-1 mb-3">
                                <label className="text-sm font-medium mb-1 block">Select Engineer</label>
                                <select
                                    className="input-field"
                                    value={selectedEngineerId}
                                    onChange={(e) => setSelectedEngineerId(Number(e.target.value))}
                                    required
                                >
                                    <option value="">Select an engineer...</option>
                                    {engineers.map(eng => (
                                        <option key={eng.id} value={eng.id}>{eng.name}</option>
                                    ))}
                                </select>
                            </div>
                            <div style={{ display: 'flex', gap: '1rem', marginTop: '1.5rem', justifyContent: 'flex-end' }}>
                                <Button type="button" variant="outline" onClick={() => setIsAssignOpen(false)}>Cancel</Button>
                                <Button type="submit">Assign</Button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};
