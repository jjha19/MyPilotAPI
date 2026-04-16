package com.MyPilot.api.controller;

import com.MyPilot.api.model.Conductor;
import com.MyPilot.api.service.ConductorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conductores")
public class ConductorController {

    private final ConductorService service;

    public ConductorController(ConductorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Conductor> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conductor> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Conductor guardar(@RequestBody Conductor conductor) {
        return service.guardar(conductor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conductor> actualizar(@PathVariable Long id, @RequestBody Conductor conductor) {
        Conductor actualizado = service.actualizar(id, conductor);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ubicacion")
    public ResponseEntity<Conductor> actualizarUbicacion(@PathVariable Long id, @RequestBody UbicacionRequest request) {
        Conductor actualizado = service.actualizarUbicacion(id, request.getLat(), request.getLng());
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    public static class UbicacionRequest {
        private Double lat;
        private Double lng;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }
    }
}

