import { Routes, Route } from 'react-router-dom';
import { Layout } from '../../components/Layout';
import { UserTasks } from './UserTasks';
import { UserProfile } from './UserProfile';

export const UserDashboard = () => {
    return (
        <Layout>
            <Routes>
                <Route path="/" element={<UserTasks />} />
                <Route path="/profile" element={<UserProfile />} />
            </Routes>
        </Layout>
    );
};
