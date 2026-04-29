package org.example.Steps;

import io.cucumber.java.en.*;
import org.example.Gestor;
import org.example.Tarea;
import static org.junit.Assert.*;

public class IdUnicoStep {

    private Gestor gestor;
    private Tarea primeraTarea;
    private Tarea segundaTarea;

    @Given("que el usuario está en el sistema de gestión de tareas")
    public void inicializarGestorIdUnico() {
        gestor = new Gestor();
    }

    @When("crea una tarea con descripción {string} y prioridad {string}")
    public void crearPrimeraTarea(String descripcion, String prioridad) {
        // Instanciamos el objeto Tarea (el ID 0 se sobrescribe adentro de tu Gestor)
        Tarea nuevaTarea = new Tarea(0, descripcion, "2026-04-08", prioridad);

        // Se usa el metodo real 'crear' para obtener la tarea con el ID asignado
        primeraTarea = gestor.crear(nuevaTarea);
    }

    @And("crea otra tarea con descripción {string} y prioridad {string}")
    public void crearSegundaTarea(String descripcion, String prioridad) {
        Tarea nuevaTarea = new Tarea(0, descripcion, "2026-04-08", prioridad);
        segundaTarea = gestor.crear(nuevaTarea);
    }

    @Then("el sistema debe haber asignado un ID a la primera tarea")
    public void validarIdPrimeraTarea() {
        // Corrección: Usamos assertNotEquals en lugar de assertTrue con !=
        assertNotEquals("La primera tarea debe tener un ID válido", 0, primeraTarea.getId());
    }

    @And("el sistema debe haber asignado un ID a la segunda tarea")
    public void validarIdSegundaTarea() {
        // Corrección: Usamos assertNotEquals en lugar de assertTrue con !=
        assertNotEquals("La segunda tarea debe tener un ID válido", 0, segundaTarea.getId());
    }

    @And("los IDs generados para ambas tareas deben ser diferentes")
    public void compararIdsGenerados() {
        assertNotEquals("Los IDs deben ser únicos", primeraTarea.getId(), segundaTarea.getId());
    }
}