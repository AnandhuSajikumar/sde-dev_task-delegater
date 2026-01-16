import React, { useEffect, useState } from 'react';
import { getAllTasks, getAllEngineers } from '../services/api';
import { Link } from 'react-router-dom';

const Dashboard = () => {
    const [stats, setStats] = useState({
        totalTasks: 0,
        openTasks: 0,
        inProgressTasks: 0,
        doneTasks: 0,
        totalEngineers: 0
    });
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [tasksRes, engRes] = await Promise.all([
                    getAllTasks({ size: 1000 }),
                    getAllEngineers({ size: 1000 })
                ]);

                const tasks = tasksRes.data.content;
                const totalEngineers = engRes.data.content.length;

                const openTasks = tasks.filter(t => t.taskStatus === 'OPEN').length;
                const inProgressTasks = tasks.filter(t => t.taskStatus === 'IN_PROGRESS').length;
                const doneTasks = tasks.filter(t => t.taskStatus === 'DONE').length;

                setStats({
                    totalTasks: tasks.length,
                    openTasks,
                    inProgressTasks,
                    doneTasks,
                    totalEngineers
                });
            } catch (err) {
                console.error(err);
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, []);

    const StatCard = ({ title, value, color, link }) => (
        <Link to={link || '#'} className="card flex flex-col items-start justify-center no-underline hover:scale-105 transition-transform" style={{ textDecoration: 'none', height: '100%', borderTop: `4px solid ${color || 'var(--primary)'}` }}>
            <p className="text-secondary uppercase tracking-widest text-xs font-bold mb-1">{title}</p>
            <h3 style={{ fontSize: '2rem', margin: 0, color: 'var(--text-primary)' }}>{value}</h3>
        </Link>
    );

    return (
        <div className="container" style={{ paddingBottom: '2rem' }}>
            <div className="flex justify-between items-center mt-8 mb-6">
                <h1 style={{ margin: 0, fontSize: '1.5rem' }}>Dashboard</h1>
                <button className="btn btn-primary" onClick={() => window.location.href = '/tasks'}>
                    Create Task
                </button>
            </div>

            {loading ? (
                <div className="text-center py-8">Loading Statistics...</div>
            ) : (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                    <StatCard title="Total Tasks" value={stats.totalTasks} color="var(--info)" link="/tasks" />
                    <StatCard title="Open" value={stats.openTasks} color="var(--info)" link="/tasks" />
                    <StatCard title="In Progress" value={stats.inProgressTasks} color="var(--warning)" link="/tasks" />
                    <StatCard title="Done" value={stats.doneTasks} color="var(--success)" link="/tasks" />

                    <div className="col-span-1 lg:col-span-4 mt-8">
                        <div className="card flex items-center justify-between">
                            <div>
                                <h2 className="text-xl mb-1">Team Overview</h2>
                                <p className="text-secondary" style={{ margin: 0 }}>Manage your engineering team members and assignments.</p>
                            </div>
                            <div className="flex items-center gap-4">
                                <div className="text-right">
                                    <span className="text-2xl font-bold block">{stats.totalEngineers}</span>
                                    <span className="text-xs text-secondary uppercase">Engineers</span>
                                </div>
                                <Link to="/engineers" className="btn btn-secondary">View Team</Link>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Dashboard;
