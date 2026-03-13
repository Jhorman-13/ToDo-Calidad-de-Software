package org.example;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class Gestor {

    private final List<Tarea> lista = new ArrayList<>();
    private int contador = 1;

    public Tarea crear(Tarea tarea) {

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