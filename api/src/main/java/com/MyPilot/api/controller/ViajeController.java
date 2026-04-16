package com.MyPilot.api.controller;

import com.MyPilot.api.model.EstadoViaje;
import com.MyPilot.api.model.Viaje;
import com.MyPilot.api.service.ViajeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viajes")
public class ViajeController {

    private final ViajeService service;

    public ViajeController(ViajeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Viaje> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Viaje> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Viaje guardar(@RequestBody Viaje viaje) {
        return service.guardar(viaje);
    }

    @GetMapping("/pendientes")
    public List<Viaje> obtenerPendientes() {
        return service.obtenerPendientes();
    }

    @PutMapping("/{id}/aceptar")
    public ResponseEntity<Viaje> aceptarViaje(@PathVariable Long id, @RequestParam Long conductorId) {
        Viaje viaje = service.aceptarViaje(id, conductorId);
        if (viaje == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(viaje);
    }

    @PutMapping("/{id}/rechazar")
    public ResponseEntity<Viaje> rechazarViaje(@PathVariable Long id) {
        Viaje viaje = service.rechazarViaje(id);
        if (viaje == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(viaje);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Viaje> actualizarEstado(@PathVariable Long id, @RequestBody EstadoRequest request) {
        Viaje viaje = service.actualizarEstado(id, request.getEstado());
        if (viaje == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(viaje);
    }

    public static class EstadoRequest {
        private EstadoViaje estado;

        public EstadoViaje getEstado() {
            return estado;
        }

        public void setEstado(EstadoViaje estado) {
            this.estado = estado;
        }
    }
}

