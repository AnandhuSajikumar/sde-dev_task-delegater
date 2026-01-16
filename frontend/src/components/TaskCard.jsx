import React from 'react';
import { Link } from 'react-router-dom';

const TaskStatusBadge = ({ status }) => {
    return (
        <span className={`badge badge-${status.toLowerCase()}`}>
            {status.replace('_', ' ')}
        </span>
    );
};

const TaskCard = ({ task, engineerName, onAssign, onUnassign, onComplete, onDelete }) => {
    const isAssigned = !!task.engineerId;

    return (
        <div className="card flex flex-col justify-between" style={{ height: '100%', borderLeft: '4px solid var(--border-color)', padding: '1rem' }}>
            <div>
                <Link to={`/tasks/${task.id}`} style={{ textDecoration: 'none', color: 'inherit' }}>
                    <h3 style={{ margin: '0 0 0.5rem 0', fontSize: '1rem', fontWeight: 600, color: 'var(--text-primary)' }}>
                        {task.title}
                    </h3>
                </Link>

                <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', marginBottom: '1rem' }}>
                    <TaskStatusBadge status={task.taskStatus} />
                    <small style={{ color: 'var(--text-secondary)' }}>TO-{task.id}</small>
                </div>

                <div className="mb-4">
                    {isAssigned ? (
                        <div className="flex items-center gap-2">
                            <div style={{ width: '24px', height: '24px', backgroundColor: '#DFE1E6', borderRadius: '50%', display: 'flex', alignItems: 'center', justifyContent: 'center', fontSize: '10px', fontWeight: 'bold', color: '#172B4D' }}>
                                {engineerName ? engineerName.charAt(0).toUpperCase() : '?'}
                            </div>
                            <span style={{ fontSize: '0.875rem', color: 'var(--text-secondary)' }}>
                                {engineerName}
                            </span>
                        </div>
                    ) : (
                        <span style={{ fontSize: '0.875rem', color: 'var(--text-secondary)', fontStyle: 'italic' }}>
                            Unassigned
                        </span>
                    )}
                </div>
            </div>

            <div className="flex items-center justify-end gap-2 mt-2" style={{ borderTop: '1px solid var(--border-color)', paddingTop: '0.75rem' }}>
                {task.taskStatus === 'OPEN' && (
                    <button className="btn btn-secondary" style={{ fontSize: '12px', padding: '4px 8px' }} onClick={() => onAssign(task)}>
                        Assign
                    </button>
                )}

                {task.taskStatus === 'IN_PROGRESS' && (
                    <>
                        <button className="btn btn-primary" style={{ fontSize: '12px', padding: '4px 8px', backgroundColor: 'var(--success-bg)', color: 'var(--success)' }} onClick={() => onComplete(task)}>
                            Done
                        </button>
                        <button className="btn btn-secondary" style={{ fontSize: '12px', padding: '4px 8px' }} onClick={() => onUnassign(task)}>
                            Unassign
                        </button>
                    </>
                )}

                <button className="btn btn-danger" style={{ fontSize: '12px', padding: '4px 8px' }} onClick={() => onDelete(task)}>
                    Delete
                </button>
            </div>
        </div>
    );
};

export default TaskCard;
