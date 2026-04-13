package com.MyPilot.api.controller;

import com.MyPilot.api.model.Coche;
import com.MyPilot.api.service.CocheService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/coches")
public class CocheController {

    private final CocheService service;

    public CocheController(CocheService service) {
        this.service = service;
    }

    // GET /api/coches → devuelve todos
    @GetMapping
    public List<Coche> getAll() {
        return service.obtenerTodos();
    }

    // GET /api/coches/{id} → devuelve uno por id
    @GetMapping("/{id}")
    public ResponseEntity<Coche> getById(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/coches → crea uno nuevo
    @PostMapping
    public Coche create(@RequestBody Coche coche) {
        return service.guardar(coche);
    }

    // PUT /api/coches/{id} → actualiza por id
    @PutMapping("/{id}")
    public ResponseEntity<Coche> update(@PathVariable Long id, @RequestBody Coche coche) {
        Coche actualizado = service.actualizar(id, coche);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    // DELETE /api/coches/{id} → elimina por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
