import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";

function Login() {

    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    const handleLogin = async (e) => {

        e.preventDefault();

        try {

            const response = await axios.post(
                "http://localhost:8080/auth/login",
                {
                    username,
                    password
                }
            );

            setMessage(response.data);

            if (response.data.toLowerCase().includes("exitoso")) {

                localStorage.setItem("username", username);

                navigate("/dashboard");
            }

        } catch (error) {

            setMessage("Error al iniciar sesión");
        }
    };

    return (

        <div className="card">

            <h2 className="subtitle">Login</h2>

            <form onSubmit={handleLogin}>

                <input
                    className="input"
                    type="text"
                    placeholder="Usuario"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />

                <input
                    className="input"
                    type="password"
                    placeholder="Contraseña"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />

                <button className="button" type="submit">
                    Ingresar
                </button>

            </form>

            <p className="message">{message}</p>

            <div className="links">

                <Link to="/register">
                    Registrarse
                </Link>

                <br /><br />

                <Link to="/recover">
                    Recuperar contraseña
                </Link>

            </div>

        </div>
    );
}

export default Login;