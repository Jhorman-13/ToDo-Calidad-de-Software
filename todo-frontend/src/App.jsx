import { useState, useEffect } from 'react'
import './App.css'

function App() {
  const [tareas, setTareas] = useState([])
  const [loading, setLoading] = useState(true)

  // Estados para capturar los datos del formulario de creación
  const [descripcion, setDescripcion] = useState("")
  const [fecha, setFecha] = useState("")
  const [prioridad, setPrioridad] = useState("Media")

  // 1. OBTENER TAREAS (GET /tareas) - Blindado contra errores 404/500
  const obtenerTareas = () => {
    fetch('http://localhost:8081/tareas')
        .then(response => {
          // Si la respuesta no es un 200 OK, forzamos un error para que vaya al .catch
          if (!response.ok) {
            throw new Error(`Error del servidor: ${response.status}`)
          }
          return response.json()
        })
        .then(data => {
          // Nos aseguramos al 100% de que lo que llegó sea un arreglo
          setTareas(Array.isArray(data) ? data : [])
          setLoading(false)
        })
        .catch(error => {
          console.error("Error al conectar con el backend:", error)
          setTareas([]) // Si falla, dejamos el arreglo vacío para que no crashee .map()
          setLoading(false)
        })
  }

  useEffect(() => {
    obtenerTareas()
  }, [])

  // 2. CREAR UNA NUEVA TAREA (POST /tareas)
  const manejarCrearTarea = (e) => {
    e.preventDefault()

    const nuevaTarea = {
      descripcion: descripcion,
      fecha: fecha,
      prioridad: prioridad
    }

    fetch('http://localhost:8081/tareas', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(nuevaTarea)
    })
        .then(response => {
          if (response.ok) {
            setDescripcion("")
            setFecha("")
            setPrioridad("Media")
            obtenerTareas()
          } else {
            alert("Error en las validaciones del servidor. Revisa los datos enviados.")
          }
        })
        .catch(error => console.error("Error al crear la tarea:", error))
  }

  // 3. COMPLETAR TAREA (PUT /tareas/{id}/completar)
  const manejarCompletarTarea = (id) => {
    fetch(`http://localhost:8081/tareas/${id}/completar`, {
      method: 'PUT'
    })
        .then(response => {
          if (response.ok) {
            obtenerTareas()
          }
        })
        .catch(error => console.error("Error al completar la tarea:", error))
  }

  // 4. ELIMINAR TAREA (DELETE /tareas/{id})
  const manejarEliminarTarea = (id) => {
    fetch(`http://localhost:8081/tareas/${id}`, {
      method: 'DELETE'
    })
        .then(response => {
          if (response.ok) {
            obtenerTareas()
          }
        })
        .catch(error => console.error("Error al eliminar la tarea:", error))
  }

  return (
      <div style={{ maxWidth: '700px', margin: '40px auto', padding: '20px', fontFamily: 'Arial, sans-serif' }}>
        <h1 style={{ textAlign: 'center', color: '#646cff' }}>LockiToDo</h1>

        {/* Formulario de Registro */}
        <form onSubmit={manejarCrearTarea} style={{ background: '#1a1a1a', padding: '20px', borderRadius: '8px', marginBottom: '30px', boxShadow: '0 4px 10px rgba(0,0,0,0.3)', border: '1px solid #333' }}>
          <h3 style={{ marginTop: 0, color: '#fff' }}>Añadir Nueva Tarea</h3>
          <div style={{ display: 'flex', flexDirection: 'column', gap: '15px', textAlign: 'left' }}>

            <div>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold', color: '#bbb' }}>Descripción:</label>
              <input
                  type="text"
                  value={descripcion}
                  onChange={(e) => setDescripcion(e.target.value)}
                  placeholder="Ej. Terminar el reporte de JMeter"
                  style={{ width: '100%', padding: '10px', borderRadius: '4px', border: '1px solid #444', backgroundColor: '#2a2a2a', color: '#fff', boxSizing: 'border-box' }}
                  required
              />
            </div>

            <div style={{ display: 'flex', gap: '15px' }}>
              <div style={{ flex: 1 }}>
                <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold', color: '#bbb' }}>Fecha:</label>
                <input
                    type="date"
                    value={fecha}
                    onChange={(e) => setFecha(e.target.value)}
                    style={{ width: '100%', padding: '10px', borderRadius: '4px', border: '1px solid #444', backgroundColor: '#2a2a2a', color: '#fff', boxSizing: 'border-box' }}
                    required
                />
              </div>

              <div style={{ flex: 1 }}>
                <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold', color: '#bbb' }}>Prioridad:</label>
                <select
                    value={prioridad}
                    onChange={(e) => setPrioridad(e.target.value)}
                    style={{ width: '100%', padding: '10px', borderRadius: '4px', border: '1px solid #444', backgroundColor: '#2a2a2a', color: '#fff', boxSizing: 'border-box' }}
                >
                  <option value="Alta">Alta</option>
                  <option value="Media">Media</option>
                  <option value="Baja">Baja</option>
                </select>
              </div>
            </div>

            <button type="submit" style={{ background: '#646cff', color: 'white', padding: '12px', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold', fontSize: '16px', marginTop: '5px' }}>
              Guardar Tarea en Backend
            </button>
          </div>
        </form>

        {/* Listado de Tareas */}
        <h2 style={{ textAlign: 'left', borderBottom: '2px solid #333', paddingBottom: '10px' }}>Tus Pendientes</h2>
        {loading ? (
            <p>Conectando con el contenedor Docker de Spring Boot...</p>
        ) : (
            <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
              {/* Validación extra con Array.isArray antes del .map */}
              {Array.isArray(tareas) && tareas.map(t => (
                  <div key={t.id} style={{
                    display: 'flex',
                    justifyContent: 'space-between',
                    alignItems: 'center',
                    padding: '15px',
                    border: '1px solid #333',
                    borderRadius: '8px',
                    backgroundColor: t.completada ? '#1b3a1b' : '#242424',
                    boxShadow: '0 2px 5px rgba(0,0,0,0.2)',
                    gap: '20px'
                  }}>

                    {/* Contenedor de Texto */}
                    <div style={{ textAlign: 'left', flex: 1, minWidth: 0 }}>
                      <h4 style={{
                        margin: '0 0 5px 0',
                        textDecoration: t.completada ? 'line-through' : 'none',
                        color: t.completada ? '#888' : '#fff',
                        fontSize: '18px',
                        wordBreak: 'break-word'
                      }}>
                        {t.descripcion}
                      </h4>
                      <span style={{ color: '#aaa', fontSize: '14px', display: 'block' }}>
                        Fecha: {t.fecha} | Prioridad: <strong>{t.prioridad}</strong>
                      </span>
                    </div>

                    {/* Contenedor de Botones */}
                    <div style={{ display: 'flex', gap: '10px', flexShrink: 0 }}>
                      {!t.completada && (
                          <button
                              onClick={() => manejarCompletarTarea(t.id)}
                              style={{ background: '#2e7d32', color: 'white', border: 'none', padding: '8px 14px', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' }}>
                            Completar
                          </button>
                      )}
                      <button
                          onClick={() => manejarEliminarTarea(t.id)}
                          style={{ background: '#d32f2f', color: 'white', border: 'none', padding: '8px 14px', borderRadius: '4px', cursor: 'pointer', fontWeight: 'bold' }}>
                        Eliminar
                      </button>
                    </div>

                  </div>
              ))}

              {/* Condicional seguro para cuando no hay tareas */}
              {(!Array.isArray(tareas) || tareas.length === 0) && (
                  <p style={{ color: '#888', fontStyle: 'italic', marginTop: '20px' }}>
                    No hay tareas registradas o el servidor está desconectado.
                  </p>
              )}
            </div>
        )}
      </div>
  )
}

export default App