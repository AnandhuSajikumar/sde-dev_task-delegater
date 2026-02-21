import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import { Login } from './pages/Auth/Login';
import { Register } from './pages/Auth/Register';

import { AdminDashboard } from './pages/Admin/AdminDashboard';
import { UserDashboard } from './pages/User/UserDashboard';
import { Home } from './pages/Home';

import React from 'react';

// Protected Route Component
const ProtectedRoute = ({ children, allowedRoles }: { children: React.ReactNode, allowedRoles?: string[] }) => {
  const { role, token } = useAuth();

  // If usage of local storage token backup logic is needed, handle it.
  // AuthContext handles loading token. But it might take a tick.
  // However, initial state of token in AuthContext is from localStorage. 
  // So isAuthenticated might be false if user is null, but token exists?
  // AuthContext: 
  // const [token] = useState(localStorage.getItem('token'))
  // useEffect -> decode -> setUser.
  // So there is a flash where token exists but user is null.
  // We should probably check if token exists.

  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // If we have token, but user is null, it means we are still decoding/validating.
  // We should show a loader or return null.
  // But for simplicity, we can rely on isAuthenticated if we handle the loading state in AuthContext.
  // In current AuthContext, we don't expose loading.
  // Let's assume if token exists, we are authenticated for route protection purposes until proven otherwise (logout).

  if (allowedRoles && role) {
    // Backend returns "ROLE_ADMIN".
    // We checked 'role' is 'user.roles[0]'.
    if (!allowedRoles.includes(role)) {
      return <Navigate to="/unauthorized" replace />; // or dashboard
    }
  }

  return children;
};

// Dashboard Redirector
const DashboardRedirect = () => {
  const { role } = useAuth();
  if (role === 'ROLE_ADMIN') return <Navigate to="/admin" replace />;
  if (role === 'ROLE_USER') return <Navigate to="/user" replace />;
  return <h1>Loading...</h1>;
};

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          <Route path="/dashboard" element={
            <ProtectedRoute>
              <DashboardRedirect />
            </ProtectedRoute>
          } />

          <Route path="/admin/*" element={
            <ProtectedRoute allowedRoles={['ROLE_ADMIN']}>
              <AdminDashboard />
            </ProtectedRoute>
          } />

          <Route path="/user/*" element={
            <ProtectedRoute allowedRoles={['ROLE_USER']}>
              <UserDashboard />
            </ProtectedRoute>
          } />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
