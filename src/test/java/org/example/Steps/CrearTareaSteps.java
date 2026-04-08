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
        tareaCreada = null;
        excepcionCapturada = null;
        mensajeSistema = null;
    }

    // FUSIONADO: Un solo metodo para ingresar datos completos
    @When("Se ingresa la descripción {string}, la fecha {string} y la prioridad {string}")
    public void ingresarDatosCompletos(String descripcion, String fecha, String prioridad) {
        Tarea nuevaTarea = new Tarea(0, descripcion, fecha, prioridad);
        try {
            tareaCreada = gestor.crear(nuevaTarea);
            mensajeSistema = "Tarea creada";
            excepcionCapturada = null; // Éxito: no hay error
        } catch (RuntimeException e) {
            excepcionCapturada = e;
            tareaCreada = null; // Fallo: no se crea tarea
        }
    }

    @Then("el sistema debe mostrar un mensaje confirmando que la tarea fue creada")
    public void validarMensajeExito() {
        assertNull("La creación no debió fallar", excepcionCapturada);
        assertEquals("Tarea creada", mensajeSistema);
    }

    @And("la tarea {string} debe aparecer en el listado de tareas pendientes")
    public void validarTareaEnListado(String descripcionEsperada) {
        boolean existe = gestor.listar().stream()
                .anyMatch(t -> t.getDescripcion().equals(descripcionEsperada));
        assertTrue("La tarea no se encontró en la lista", existe);
    }


    @Then("el sistema debe confirmar que la tarea fue creada exitosamente")
    public void confirmarCreacionExitosa() {
        assertNull("El sistema NO debió lanzar ningún error porque los datos eran correctos", excepcionCapturada);
        assertNotNull("La tarea debió crearse exitosamente", tareaCreada);
    }


    @Then("el sistema debe mostrar un mensaje de error indicando que la descripción es obligatoria")
    public void validarErrorDescripcion() {
        assertNotNull("Debió lanzar un error por falta de descripción", excepcionCapturada);
        assertEquals("La descripción es obligatoria", excepcionCapturada.getMessage());
    }

    //  Otros Escenarios de Campos Vacíos
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
        assertNotNull("El sistema debe enviar un error", excepcionCapturada);
        assertEquals("La fecha es obligatoria", excepcionCapturada.getMessage());
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
        assertEquals("La prioridad es obligatoria", excepcionCapturada.getMessage());
    }

}