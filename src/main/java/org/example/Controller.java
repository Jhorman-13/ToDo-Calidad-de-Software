package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tareas")
public class Controller {

    private final Gestor gestor;

    public Controller(Gestor gestor) {
        this.gestor = gestor;
    }

    @PostMapping
    public ResponseEntity<Tarea> crear(@RequestBody Tarea tarea) {
        return ResponseEntity.ok(gestor.crear(tarea));
    }

    @GetMapping
    public ResponseEntity<List<Tarea>> listar() {
        return ResponseEntity.ok(gestor.listar());
    }

    @PutMapping("/{id}/completar")
    public ResponseEntity<String> completar(@PathVariable int id) {
        gestor.completar(id);
        return ResponseEntity.ok("Tarea completada");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        gestor.eliminar(id);
        return ResponseEntity.ok("Tarea eliminada");
    }
}