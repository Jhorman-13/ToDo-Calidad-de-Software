import { Link, useNavigate } from "react-router-dom";

function Sidebar() {
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem("username");
        navigate("/login");
    };

    return (
        <div className="sidebar">
            <div className="sidebar-links">
                <Link to="/dashboard">Dashboard</Link>
                <Link to="/login">Login</Link>
                <Link to="/register">Registro</Link>
                <Link to="/recover">Recuperar</Link>
            </div>

            <button className="button logout-sidebar-btn" onClick={handleLogout}>
                Cerrar sesión
            </button>
        </div>
    );
}

export default Sidebar;