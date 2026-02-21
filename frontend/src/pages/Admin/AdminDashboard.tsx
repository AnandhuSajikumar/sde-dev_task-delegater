import { Routes, Route } from 'react-router-dom';
import { Layout } from '../../components/Layout';
import { AdminOverview } from './AdminOverview';
import { AdminEngineers } from './AdminEngineers';
import { AdminTasks } from './AdminTasks';
import { AdminUsers } from './AdminUsers';

export const AdminDashboard = () => {
    return (
        <Layout>
            <Routes>
                <Route path="/" element={<AdminOverview />} />
                <Route path="/engineers" element={<AdminEngineers />} />
                <Route path="/tasks" element={<AdminTasks />} />
                <Route path="/users" element={<AdminUsers />} />
            </Routes>
        </Layout>
    );
};
