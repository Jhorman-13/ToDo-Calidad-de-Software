package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TareaTest {

    @Test
    void tareaDeberiaInicializarCorrectamente() {

        Tarea tarea = new Tarea(1,"Estudiar","2026","Alta");

        assertEquals(1, tarea.getId());
        assertEquals("Estudiar", tarea.getDescripcion());
        assertEquals("2026", tarea.getFecha());
        assertEquals("Alta", tarea.getPrioridad());
        assertFalse(tarea.isCompletada());

    }

    @Test
    void tareaDeberiaCompletar() {

        Tarea tarea = new Tarea(1,"Test","2026","Alta");

        tarea.completar();

        assertTrue(tarea.isCompletada());
    }

    @Test
    void settersDeberianModificarValores() {

        Tarea tarea = new Tarea();

        tarea.setId(5);
        tarea.setDescripcion("Nueva");
        tarea.setFecha("2027");
        tarea.setPrioridad("Media");

        assertEquals(5, tarea.getId());
        assertEquals("Nueva", tarea.getDescripcion());
        assertEquals("2027", tarea.getFecha());
        assertEquals("Media", tarea.getPrioridad());
    }
}