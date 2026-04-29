package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.NoSuchElementException; // Importante agregar esta importación

import static org.junit.jupiter.api.Assertions.*;

class GestorTest {

    @Test
    void gestorDeberiaCrearTarea() {

        Gestor gestor = new Gestor();

        Tarea tarea = gestor.crear(
                new Tarea(0,"Test1","2026","Alta")
        );

        assertEquals(1, tarea.getId());
        assertEquals("Test1", tarea.getDescripcion());
        assertFalse(tarea.isCompletada());
    }

    @Test
    void gestorDeberiaCrearMultiplesTareas() {

        Gestor gestor = new Gestor();

        gestor.crear(new Tarea(0,"A","2026","Alta"));
        gestor.crear(new Tarea(0,"B","2026","Media"));

        List<Tarea> lista = gestor.listar();

        assertEquals(2, lista.size());
    }

    @Test
    void idsDebenSerConsecutivos() {

        Gestor gestor = new Gestor();

        Tarea t1 = gestor.crear(new Tarea(0,"A","2026","Alta"));
        Tarea t2 = gestor.crear(new Tarea(0,"B","2026","Alta"));

        assertEquals(1, t1.getId());
        assertEquals(2, t2.getId());
    }

    @Test
    void listarDebeRetornarContenidoCorrecto() {

        Gestor gestor = new Gestor();

        gestor.crear(new Tarea(0,"A","2026","Alta"));

        List<Tarea> lista = gestor.listar();

        assertEquals("A", lista.get(0).getDescripcion());
    }

    @Test
    void gestorDeberiaCompletarTarea() {

        Gestor gestor = new Gestor();

        Tarea tarea = gestor.crear(
                new Tarea(0,"Completar","2026","Alta")
        );

        gestor.completar(tarea.getId());

        assertTrue(tarea.isCompletada());
    }

    @Test
    void completarSoloDebeAfectarUnaTarea() {

        Gestor gestor = new Gestor();

        Tarea t1 = gestor.crear(new Tarea(0,"A","2026","Alta"));
        Tarea t2 = gestor.crear(new Tarea(0,"B","2026","Alta"));

        gestor.completar(t1.getId());

        assertTrue(t1.isCompletada());
        assertFalse(t2.isCompletada());
    }

    @Test
    void completarTareaInexistenteDebeLanzarError() {

        Gestor gestor = new Gestor();

        // Actualizado a la excepción específica
        assertThrows(NoSuchElementException.class, () -> {
            gestor.completar(999);
        });
    }

    @Test
    void gestorDeberiaEliminarTarea() {

        Gestor gestor = new Gestor();

        Tarea tarea = gestor.crear(
                new Tarea(0,"Eliminar","2026","Alta")
        );

        gestor.eliminar(tarea.getId());

        assertEquals(0, gestor.listar().size());
    }

    @Test
    void eliminarDebeEliminarLaTareaCorrecta() {

        Gestor gestor = new Gestor();

        Tarea t1 = gestor.crear(new Tarea(0,"A","2026","Alta"));
        // Se corrige el Code Smell: se crea la tarea sin guardarla en una variable que no se usa
        gestor.crear(new Tarea(0,"B","2026","Alta"));

        gestor.eliminar(t1.getId());

        assertEquals(1, gestor.listar().size());
        assertEquals("B", gestor.listar().get(0).getDescripcion());
    }

    @Test
    void eliminarTareaInexistenteDebeLanzarError() {

        Gestor gestor = new Gestor();

        // Actualizado a la excepción específica
        assertThrows(NoSuchElementException.class, () -> {
            gestor.eliminar(999);
        });
    }
}