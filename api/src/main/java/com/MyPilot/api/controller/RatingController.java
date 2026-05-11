package com.MyPilot.api.controller;

import com.MyPilot.api.model.Rating;
import com.MyPilot.api.service.RatingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService service;

    public RatingController(RatingService service) {
        this.service = service;
    }

    @PostMapping("/{viajeId}/conductor")
    public Rating registrarPuntuacionConductor(
            @PathVariable Long viajeId,
            @RequestParam Integer puntuacion) {
        return service.registrarPuntuacionConductor(viajeId, puntuacion);
    }

    @PostMapping("/{viajeId}/viajero")
    public Rating registrarPuntuacionViajero(
            @PathVariable Long viajeId,
            @RequestParam Integer puntuacion) {
        return service.registrarPuntuacionViajero(viajeId, puntuacion);
    }

    @GetMapping("/viajero/{viajeroId}/promedio")
    public Double obtenerPromedioViajero(@PathVariable Long viajeroId) {
        return service.calcularPromedioViajero(viajeroId);
    }

    @GetMapping("/conductor/{conductorId}/promedio")
    public Double obtenerPromedioConductor(@PathVariable Long conductorId) {
        return service.calcularPromedioConductor(conductorId);
    }
}
