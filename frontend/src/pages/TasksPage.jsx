import React, { useEffect, useState } from 'react';
import { getAllTasks, getTasksByStatus, createTask, deleteTask, assignTask, unassignTask, completeTask, getAllEngineers } from '../services/api';
import TaskCard from '../components/TaskCard';
import Modal from '../components/Modal';
import TaskForm from '../components/TaskForm';

const TasksPage = () => {
    const [tasks, setTasks] = useState([]);
    const [engineers, setEngineers] = useState({}); // Map id -> name
    const [engineerList, setEngineerList] = useState([]); // List for assignment
    const [loading, setLoading] = useState(true);
    const [filter, setFilter] = useState('ALL');

    const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
    const [isAssignModalOpen, setIsAssignModalOpen] = useState(false);
    const [selectedTask, setSelectedTask] = useState(null);

    const [assignmentEngineerId, setAssignmentEngineerId] = useState('');

    const fetchTasks = async () => {
        setLoading(true);
        try {
            let res;
            if (filter === 'ALL') {
                res = await getAllTasks({ size: 100 });
            } else {
                res = await getTasksByStatus(filter, { size: 100 });
            }
            // Backend returns Page<TaskResponse>, content is in res.data.content
            setTasks(res.data.content);
        } catch (err) {
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const fetchEngineers = async () => {
        try {
            const res = await getAllEngineers({ size: 100 });
            const list = res.data.content;
            setEngineerList(list);
            const map = {};
            list.forEach(e => map[e.id] = e.name);
            setEngineers(map);
        } catch (err) {
            console.error(err);
        }
    };

    useEffect(() => {
        fetchEngineers();
    }, []);

    useEffect(() => {
        fetchTasks();
    }, [filter]);

    const handleCreate = async (data) => {
        try {
            await createTask(data);
            setIsCreateModalOpen(false);
            fetchTasks();
        } catch (err) {
            alert('Failed to create task');
        }
    };

    const handleDelete = async (task) => {
        if (!window.confirm('Are you sure?')) return;
        try {
            await deleteTask(task.id);
            fetchTasks();
        } catch (err) {
            alert('Failed to delete task');
        }
    };

    const handleAssignClick = (task) => {
        setSelectedTask(task);
        if (engineerList.length > 0) setAssignmentEngineerId(engineerList[0].id);
        setIsAssignModalOpen(true);
    };

    const submitAssignment = async () => {
        try {
            await assignTask(selectedTask.id, assignmentEngineerId);
            setIsAssignModalOpen(false);
            fetchTasks();
        } catch (err) {
            alert('Failed to assign task');
        }
    };

    const handleUnassign = async (task) => {
        if (!window.confirm('Unassign task?')) return;
        try {
            await unassignTask(task.id);
            fetchTasks();
        } catch (err) {
            alert('Failed to unassign task');
        }
    };

    const handleComplete = async (task) => {
        if (!window.confirm('Mark as complete?')) return;
        try {
            // Need the assigned engineer ID. It should be in the task object
            await completeTask(task.id, task.engineerId);
            fetchTasks();
        } catch (err) {
            // Backend throws if engineer doesn't match, or not assigned.
            alert('Failed to complete task. ' + (err.response?.data?.message || ''));
        }
    };

    return (
        <div className="container">
            <div className="flex flex-col md:flex-row items-center justify-between mt-8 mb-6 gap-4">
                <div className="flex items-center gap-4">
                    <h1>Tasks</h1>
                    <select
                        value={filter}
                        onChange={(e) => setFilter(e.target.value)}
                        style={{ width: 'auto', minWidth: '150px' }}
                    >
                        <option value="ALL">All Status</option>
                        <option value="OPEN">Open</option>
                        <option value="IN_PROGRESS">In Progress</option>
                        <option value="DONE">Done</option>
                    </select>
                </div>
                <button className="btn btn-primary" onClick={() => setIsCreateModalOpen(true)}>
                    + New Task
                </button>
            </div>

            {loading ? (
                <div className="text-center py-8">Loading tasks...</div>
            ) : tasks.length === 0 ? (
                <div className="text-center py-8 text-secondary">
                    No tasks found. Create one to get started!
                </div>
            ) : (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3">
                    {tasks.map(task => (
                        <TaskCard
                            key={task.id}
                            task={task}
                            engineerName={engineers[task.engineerId]}
                            onAssign={handleAssignClick}
                            onUnassign={handleUnassign}
                            onComplete={handleComplete}
                            onDelete={handleDelete}
                        />
                    ))}
                </div>
            )}

            {/* Create Modal */}
            <Modal
                isOpen={isCreateModalOpen}
                onClose={() => setIsCreateModalOpen(false)}
                title="Create New Task"
            >
                <TaskForm onSubmit={handleCreate} onCancel={() => setIsCreateModalOpen(false)} />
            </Modal>

            {/* Assign Modal */}
            <Modal
                isOpen={isAssignModalOpen}
                onClose={() => setIsAssignModalOpen(false)}
                title={`Assign Task: ${selectedTask?.title}`}
            >
                <div className="flex flex-col gap-4">
                    <div>
                        <label className="block mb-2 text-secondary">Select Engineer</label>
                        <select
                            value={assignmentEngineerId}
                            onChange={(e) => setAssignmentEngineerId(e.target.value)}
                        >
                            {engineerList.map(eng => (
                                <option key={eng.id} value={eng.id}>{eng.name} ({eng.techStack})</option>
                            ))}
                        </select>
                    </div>
                    <div className="flex justify-end gap-2 mt-4">
                        <button className="btn btn-secondary" onClick={() => setIsAssignModalOpen(false)}>Cancel</button>
                        <button className="btn btn-primary" onClick={submitAssignment}>Assign</button>
                    </div>
                </div>
            </Modal>
        </div>
    );
};

export default TasksPage;
