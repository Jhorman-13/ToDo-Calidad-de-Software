package org.example;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class Gestor {

    private final List<Tarea> lista = new ArrayList<>();
    private int contador = 1;

    public Tarea crear(Tarea tarea) {

        // --- INICIO DE VALIDACIONES ---
        // Verificamos que la descripción no sea nula ni esté vacía (ignorando espacios en blanco con trim)
        if (tarea.getDescripcion() == null || tarea.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción es obligatoria");
        }

        // Verificamos que la fecha no sea nula ni esté vacía
        if (tarea.getFecha() == null || tarea.getFecha().trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }

        // Verificamos que la prioridad no sea nula ni esté vacía
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
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        tarea.completar();
    }

    public void eliminar(int id) {
        boolean eliminada = lista.removeIf(t -> t.getId() == id);

        if (!eliminada) {
            throw new RuntimeException("Tarea no encontrada");
        }
    }
}