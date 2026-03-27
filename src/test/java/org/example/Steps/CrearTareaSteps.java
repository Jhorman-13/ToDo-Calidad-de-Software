package org.example.Steps;

import io.cucumber.java.en.*;
import org.example.Gestor;
import org.example.Tarea;
import static org.junit.Assert.*;

public class CrearTareaSteps {

    private Gestor gestor;
    private Tarea tareaCreada;
    private Exception excepcionCapturada;
    private String mensajeSistema;

    @Given("que el usuario ha iniciado el sistema y está en la sección del menú crear tarea")
    public void prepararSistemaParaCreacion() {
        gestor = new Gestor();
        excepcionCapturada = null;
        mensajeSistema = null;
    }

    @When("Se ingresa la descripción {string}, la fecha {string} y la prioridad {string}")
    public void ingresarDatosCompletos(String descripcion, String fecha, String prioridad) {
        try {
            // Creamos la Tarea y usamos tu método real 'crear'
            Tarea nuevaTarea = new Tarea(0, descripcion, fecha, prioridad);
            tareaCreada = gestor.crear(nuevaTarea);
            mensajeSistema = "Tarea creada";
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Then("el sistema debe mostrar un mensaje confirmando que la tarea fue creada")
    public void validarMensajeExito() {
        assertNull("La creación no debió fallar", excepcionCapturada);
        assertEquals("Tarea creada", mensajeSistema);
    }

    @And("la tarea {string} debe aparecer en el listado de tareas pendientes")
    public void validarTareaEnListado(String descripcionEsperada) {
        // Cambiamos obtenerTareas() por tu método real listar()
        boolean existe = gestor.listar().stream()
                .anyMatch(t -> t.getDescripcion().equals(descripcionEsperada));
        assertTrue("La tarea no se encontró en la lista", existe);
    }

    // --- Validaciones de campos vacíos ---

    @When("Se ingresa la fecha {string} y la prioridad {string} sin ingresar la descripción")
    public void crearSinDescripcion(String fecha, String prioridad) {
        try {
            Tarea nuevaTarea = new Tarea(0, "", fecha, prioridad);
            gestor.crear(nuevaTarea);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Then("el sistema debe mostrar un mensaje de error indicando que la descripción es obligatoria")
    public void validarErrorDescripcion() {
        assertNotNull("Debió lanzar un error por falta de descripción", excepcionCapturada);
    }

    @When("Se ingresa la descripción {string} y la prioridad {string} sin ingresar la fecha")
    public void crearSinFecha(String descripcion, String prioridad) {
        try {
            Tarea nuevaTarea = new Tarea(0, descripcion, "", prioridad);
            gestor.crear(nuevaTarea);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Then("el sistema debe mostrar un mensaje de error indicando que la fecha es obligatoria")
    public void validarErrorFecha() {
        assertNotNull("Debió lanzar un error por falta de fecha", excepcionCapturada);
    }

    @When("Se ingresa la descripción {string} y la fecha {string} sin ingresar la prioridad")
    public void crearSinPrioridad(String descripcion, String fecha) {
        try {
            Tarea nuevaTarea = new Tarea(0, descripcion, fecha, "");
            gestor.crear(nuevaTarea);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Then("el sistema debe mostrar un mensaje de error indicando que la prioridad es obligatoria")
    public void validarErrorPrioridad() {
        assertNotNull("Debió lanzar un error por falta de prioridad", excepcionCapturada);
    }
}