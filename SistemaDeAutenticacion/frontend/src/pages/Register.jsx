import { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

function Register() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    const handleRegister = async (e) => {

        e.preventDefault();

        try {

            const response = await axios.post(
                "http://localhost:8080/auth/register",
                {
                    username,
                    password
                }
            );

            setMessage(response.data);

        } catch (error) {

            setMessage("Error al registrar usuario");
        }
    };

    return (

        <div className="card">

            <h2 className="subtitle">Registro</h2>

            <form onSubmit={handleRegister}>

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
                    Registrarse
                </button>

            </form>

            <p className="message">{message}</p>

            <div className="links">

                <Link to="/login">
                    Volver al login
                </Link>

            </div>

        </div>
    );
}

export default Register;