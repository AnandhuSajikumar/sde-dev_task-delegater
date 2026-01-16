import axios from 'axios';

const api = axios.create({
    baseURL: '/api/v1',
    headers: {
        'Content-Type': 'application/json',
    },
});

// Task APIs
export const getAllTasks = (params) => api.get('/tasks', { params });
export const getTaskById = (id) => api.get(`/tasks/${id}`);
export const createTask = (data) => api.post('/tasks', data);
export const updateTask = (id, data) => api.put(`/tasks/${id}`, data);
export const deleteTask = (id) => api.delete(`/tasks/${id}`);
export const assignTask = (taskId, engineerId) => api.put(`/tasks/${taskId}/assign/${engineerId}`);
export const unassignTask = (taskId) => api.put(`/tasks/${taskId}/unassign`);
export const completeTask = (taskId, engineerId) => api.post(`/tasks/${taskId}/complete`, null, { params: { engineerId } });
export const getUnassignedTasks = (params) => api.get('/tasks/unassigned', { params });
export const getAssignedTasks = (params) => api.get('/tasks/assigned', { params });
export const getTasksByStatus = (status, params) => api.get('/tasks/filter', { params: { status, ...params } });

// Engineer APIs
export const getAllEngineers = (params) => api.get('/engineers', { params });
export const getEngineerById = (id) => api.get(`/engineers/${id}`);
export const createEngineer = (data) => api.post('/engineers', data);
export const updateEngineer = (id, data) => api.patch(`/engineers/update/${id}`, data);
export const deleteEngineer = (id) => api.delete(`/engineers/${id}`);
export const getTasksByEngineer = (id, params) => api.get(`/tasks/${id}/tasks`, { params });

export default api;
