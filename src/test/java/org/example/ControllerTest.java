package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    @Test
    void controllerDeberiaCrearTarea() {

        Gestor gestor = new Gestor();
        Controller controller = new Controller(gestor);

        Tarea tarea = new Tarea(0,"Controller","2026","Alta");

        ResponseEntity<Tarea> respuesta = controller.crear(tarea);

        Tarea creada = respuesta.getBody();

        assertNotNull(creada);
        assertEquals("Controller", creada.getDescripcion());
        assertEquals(1, creada.getId());
    }

    @Test
    void controllerDeberiaListarTareas() {

        Gestor gestor = new Gestor();
        Controller controller = new Controller(gestor);

        controller.crear(new Tarea(0,"A","2026","Alta"));

        ResponseEntity<List<Tarea>> respuesta = controller.listar();

        List<Tarea> lista = respuesta.getBody();

        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    void controllerDeberiaEliminarTarea() {

        Gestor gestor = new Gestor();
        Controller controller = new Controller(gestor);

        Tarea tarea = controller
                .crear(new Tarea(0,"Eliminar","2026","Alta"))
                .getBody();

        controller.eliminar(tarea.getId());

        List<Tarea> lista = controller.listar().getBody();

        assertEquals(0, lista.size());
    }

    @Test
    void controllerDebeCompletarTarea() {

        Gestor gestor = new Gestor();
        Controller controller = new Controller(gestor);

        Tarea tarea = controller
                .crear(new Tarea(0,"Completar","2026","Alta"))
                .getBody();

        controller.completar(tarea.getId());

        List<Tarea> lista = controller.listar().getBody();

        assertTrue(lista.get(0).isCompletada());
    }

    @Test
    void controllerEliminarTareaInexistenteDebeLanzarError() {

        Gestor gestor = new Gestor();
        Controller controller = new Controller(gestor);

        assertThrows(RuntimeException.class, () -> {
            controller.eliminar(999);
        });
    }

    @Test
    void controllerCompletarTareaInexistenteDebeLanzarError() {

        Gestor gestor = new Gestor();
        Controller controller = new Controller(gestor);

        assertThrows(RuntimeException.class, () -> {
            controller.completar(999);
        });
    }
}