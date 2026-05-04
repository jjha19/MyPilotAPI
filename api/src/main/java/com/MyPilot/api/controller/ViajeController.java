package com.MyPilot.api.controller;

import com.MyPilot.api.model.Viaje;
import com.MyPilot.api.model.ViajeEstado;
import com.MyPilot.api.service.ViajeService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/viajes")
public class ViajeController {

    private final ViajeService service;
    private final SimpMessagingTemplate messagingTemplate;

    public ViajeController(ViajeService service, SimpMessagingTemplate messagingTemplate) {
        this.service = service;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public Viaje crearViaje(@RequestParam Long viajeroId) {
        return service.crearViaje(viajeroId);
    }

    @GetMapping
    public List<Viaje> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<Viaje> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PutMapping("/{id}/aceptar")
    public Viaje aceptar(@PathVariable Long id) {
        Viaje viaje = service.cambiarEstado(id, ViajeEstado.CONDUCTOR_EN_CAMINO);
        messagingTemplate.convertAndSend("/topic/viajes/" + id, viaje);
        return viaje;
    }

    @PutMapping("/{id}/iniciar")
    public Viaje iniciar(@PathVariable Long id) {
        Viaje viaje = service.cambiarEstado(id, ViajeEstado.EN_CURSO);
        messagingTemplate.convertAndSend("/topic/viajes/" + id, viaje);
        return viaje;
    }

    @PutMapping("/{id}/finalizar")
    public Viaje finalizar(@PathVariable Long id) {
        Viaje viaje = service.cambiarEstado(id, ViajeEstado.FINALIZADO);
        messagingTemplate.convertAndSend("/topic/viajes/" + id, viaje);
        return viaje;
    }
}