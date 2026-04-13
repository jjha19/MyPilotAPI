package com.MyPilot.api.controller;

import com.MyPilot.api.model.Viajero;
import com.MyPilot.api.service.ViajeroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/viajeros")
public class ViajeroController {

    private final ViajeroService service;

    public ViajeroController(ViajeroService service) {
        this.service = service;
    }

    // GET /api/ → devuelve todos
    @GetMapping
    public List<Viajero> getAll() {
        return service.obtenerTodos();
    }

    // GET /api/viajero/1 → devuelve uno por id
    @GetMapping("/{id}")
    public ResponseEntity<Viajero> getById(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/viajeros → crea uno nuevo
    @PostMapping
    public Viajero create(@RequestBody Viajero viajero) {
        return service.guardar(viajero);
    }

    // DELETE /api/viajeros/1 → elimina por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // PUT /api/viajeros/1 → actualiza por id
    @PutMapping("/{id}")
    public ResponseEntity<Viajero> update(@PathVariable Long id, @RequestBody Viajero viajero) {
        Viajero actualizado = service.actualizar(id, viajero);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }
}