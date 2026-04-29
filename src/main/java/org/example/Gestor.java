package org.example;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException; // <-- Agregamos esta importación

@Service
public class Gestor {

    private final List<Tarea> lista = new ArrayList<>();
    private int contador = 1;

    public Tarea crear(Tarea tarea) {

        // --- INICIO DE VALIDACIONES ---
        if (tarea.getDescripcion() == null || tarea.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción es obligatoria");
        }

        if (tarea.getFecha() == null || tarea.getFecha().trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }

        if (tarea.getPrioridad() == null || tarea.getPrioridad().trim().isEmpty()) {
            throw new IllegalArgumentException("La prioridad es obligatoria");
        }
        // --- FIN DE VALIDACIONES ---

        Tarea nueva = new Tarea(
                contador,
                tarea.getDescripcion(),
                tarea.getFecha(),
                tarea.getPrioridad()
        );

        lista.add(nueva);
        contador++;

        return nueva;
    }

    public List<Tarea> listar() {
        return lista;
    }

    public void completar(int id) {
        Tarea tarea = lista.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                // <-- Cambiamos RuntimeException por NoSuchElementException
                .orElseThrow(() -> new NoSuchElementException("Tarea no encontrada"));

        tarea.completar();
    }

    public void eliminar(int id) {
        boolean eliminada = lista.removeIf(t -> t.getId() == id);

        if (!eliminada) {
            // <-- Cambiamos RuntimeException por NoSuchElementException
            throw new NoSuchElementException("Tarea no encontrada");
        }
    }
}