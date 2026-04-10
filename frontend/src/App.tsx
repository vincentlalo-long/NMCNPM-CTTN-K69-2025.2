import { Navigate, Route, Routes } from "react-router-dom";

import { AdminFeatureLandingPage } from "./pages/admin/AdminFeatureLandingPage";
import { AdminLayout } from "./layouts/AdminLayout.tsx";
import { AdminDashboardPage } from "./pages/admin/AdminDashboardPage.tsx";
import { FieldSchedulePage } from "./pages/admin/FieldSchedulePage";
import { SettingsPage } from "./pages/admin/SettingsPage";
import { LoginPage } from "./pages/auth/LoginPage";
import { LandingPage } from "./pages/LandingPage";
import { RegisterPage } from "./pages/auth/RegisterPage";

function App() {
  return (
    <Routes>
      <Route path="/" element={<LandingPage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/admin" element={<AdminLayout />}>
        <Route index element={<AdminDashboardPage />} />
        <Route path="users" element={<AdminDashboardPage />} />
        <Route path="bookings" element={<AdminFeatureLandingPage />} />
        <Route path="schedule" element={<FieldSchedulePage />} />
        <Route
          path="calendar"
          element={<Navigate to="/admin/schedule" replace />}
        />
        <Route path="settings" element={<SettingsPage />} />
      </Route>
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}

export default App;
