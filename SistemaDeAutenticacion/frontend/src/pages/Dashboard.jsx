import Navbar from "../components/Navbar";
import Sidebar from "../components/Sidebar";

function Dashboard() {
    const username = localStorage.getItem("username");

    return (
        <div>
            <Navbar />
            <div className="dashboard-layout">
                <Sidebar />
                <div className="dashboard-content">
                    <h2 className="dashboard-title">
                        Bienvenido {username}
                    </h2>

                    <div className="stats-grid">
                        <div className="stats-card">
                            <h3>Usuarios</h3>
                            <p>125</p>
                        </div>
                        <div className="stats-card">
                            <h3>Sesiones</h3>
                            <p>87</p>
                        </div>
                        <div className="stats-card">
                            <h3>Actividad</h3>
                            <p>95%</p>
                        </div>
                    </div>

                    <div className="card">
                        <h2 className="subtitle">
                            Información del sistema
                        </h2>
                        <p>
                            El sistema de autenticación está funcionando correctamente.
                        </p>
                        <br />
                    </div>

                    {/* --- NUEVA TARJETA PARA LA CALCULADORA --- */}
                    <div className="card" style={{ marginTop: '20px' }}>
                        <h2 className="subtitle">
                            Herramientas y Servicios
                        </h2>
                        <p style={{ marginBottom: '20px' }}>
                            Accede a los diferentes servicios integrados en tu plataforma.
                        </p>
                        <a
                            href="http://localhost:3001"
                            target="_blank"
                            rel="noopener noreferrer"
                            className="btn-calculadora"
                            style={{
                                padding: '10px 20px',
                                backgroundColor: '#28a745',
                                color: 'white',
                                textDecoration: 'none',
                                borderRadius: '5px',
                                display: 'inline-block'
                            }}
                        >
                            Abrir Calculadora Financiera
                        </a>
                    </div>
                    {/* ----------------------------------------- */}

                </div>
            </div>
        </div>
    );
}

export default Dashboard;