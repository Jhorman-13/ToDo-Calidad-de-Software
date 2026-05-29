import { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

function Recover() {

    const [username, setUsername] = useState("");
    const [message, setMessage] = useState("");

    const handleRecover = async (e) => {

        e.preventDefault();

        try {

            const response = await axios.post(
                `http://localhost:8080/auth/recover?username=${username}`
            );

            setMessage(response.data);

        } catch (error) {

            setMessage("Error al recuperar contraseña");
        }
    };

    return (

        <div className="card">

            <h2 className="subtitle">
                Recuperar Contraseña
            </h2>

            <form onSubmit={handleRecover}>

                <input
                    className="input"
                    type="text"
                    placeholder="Usuario"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />

                <button className="button" type="submit">
                    Recuperar
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

export default Recover;