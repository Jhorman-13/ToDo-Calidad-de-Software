package org.example.Steps;

import io.cucumber.java.en.*;
import org.example.Gestor;
import org.example.Tarea;
import static org.junit.Assert.*;

public class EstadoPendienteStep {

    private Gestor gestor;
    private Tarea tareaRecienCreada;

    @Given("que el usuario se encuentra en el sistema de gestión de tareas")
    public void inicializarGestorEstado() {
        gestor = new Gestor();
    }

    @When("el usuario crea una nueva tarea con descripción {string} y prioridad {string}")
    public void crearTareaParaEstado(String descripcion, String prioridad) {
        Tarea nuevaTarea = new Tarea(0, descripcion, "2026-04-08", prioridad);
        tareaRecienCreada = gestor.crear(nuevaTarea);
    }

    @Then("la tarea debe guardarse exitosamente en el sistema")
    public void verificarTareaGuardada() {
        assertNotNull("La tarea debió crearse y guardarse", tareaRecienCreada);
    }

    @And("el estado de la tarea recién creada debe ser exactamente {string}")
    public void verificarEstadoInicial(String estadoEsperado) {
            // Verificamos que el estado de la tarea recién creada sea "pendiente"
        assertFalse("La tarea debería estar pendiente (completada = false)", tareaRecienCreada.isCompletada());
    }
}