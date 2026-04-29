package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void crearDebeRetornarTareaCreada() {

        Gestor gestor = new Gestor();
        Controller controller = new Controller(gestor);

        ResponseEntity<Tarea> response =
                controller.crear(new Tarea(0, "Controller", "2026", "Alta"));

        // Eliminamos el deprecated y usamos la forma recomendada por Spring
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Controller", response.getBody().getDescripcion());
    }

    @Test
    void listarDebeRetornarListaDeTareas() {

        Gestor gestor = new Gestor();
        Controller controller = new Controller(gestor);

        controller.crear(new Tarea(0, "A", "2026", "Alta"));

        ResponseEntity<List<Tarea>> response = controller.listar();

        assertEquals(1, response.getBody().size());
        assertEquals("A", response.getBody().get(0).getDescripcion());
    }

    @Test
    void completarDebeRetornarMensajeCorrecto() {

        Gestor gestor = new Gestor();
        Controller controller = new Controller(gestor);

        Tarea tarea = controller
                .crear(new Tarea(0, "Test", "2026", "Alta"))
                .getBody();

        ResponseEntity<String> response =
                controller.completar(tarea.getId());

        assertEquals("Tarea completada", response.getBody());
    }

    @Test
    void eliminarDebeRetornarMensajeCorrecto() {

        Gestor gestor = new Gestor();
        Controller controller = new Controller(gestor);

        Tarea tarea = controller
                .crear(new Tarea(0, "Eliminar", "2026", "Alta"))
                .getBody();

        ResponseEntity<String> response =
                controller.eliminar(tarea.getId());

        assertEquals("Tarea eliminada", response.getBody());
    }

    @Test
    void completarTareaInexistenteDebeLanzarError() {

        Gestor gestor = new Gestor();
        Controller controller = new Controller(gestor);

        // Actualizamos a la excepción específica que usamos en Gestor
        assertThrows(NoSuchElementException.class, () -> {
            controller.completar(999);
        });
    }

    @Test
    void eliminarTareaInexistenteDebeLanzarError() {

        Gestor gestor = new Gestor();
        Controller controller = new Controller(gestor);

        // Actualizamos a la excepción específica que usamos en Gestor
        assertThrows(NoSuchElementException.class, () -> {
            controller.eliminar(999);
        });
    }
}