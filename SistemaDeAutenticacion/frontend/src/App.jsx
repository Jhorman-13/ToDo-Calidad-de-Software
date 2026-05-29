import { Routes, Route, Navigate, useLocation } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Recover from "./pages/Recover";
import Dashboard from "./pages/Dashboard";

import ProtectedRoute from "./components/ProtectedRoute";

import "./styles/app.css";

function App() {
    const location = useLocation();

    // Detectamos si el usuario está en el dashboard
    const isDashboard = location.pathname === "/dashboard";

    return (
        /* Si es dashboard no aplica la clase container de 400px */
        <div className={isDashboard ? "" : "container"}>

            {/* El título solo se muestra en las pantallas de login/registro/recuperar */}
            {!isDashboard && (
                <h1 className="title">
                    Sistema de Autenticación
                </h1>
            )}

            <Routes>

                <Route path="/" element={<Navigate to="/login" />} />

                <Route path="/login" element={<Login />} />

                <Route path="/register" element={<Register />} />

                <Route path="/recover" element={<Recover />} />

                <Route
                    path="/dashboard"
                    element={
                        <ProtectedRoute>
                            <Dashboard />
                        </ProtectedRoute>
                    }
                />

            </Routes>

        </div>
    );
}

export default App;