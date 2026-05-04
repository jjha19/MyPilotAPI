package com.MyPilot.api.service;

import com.MyPilot.api.model.Viaje;
import com.MyPilot.api.model.ViajeEstado;
import com.MyPilot.api.repository.ConductorRepository;
import com.MyPilot.api.repository.ViajeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ViajeService {

    private final ViajeRepository viajeRepository;
    private final ConductorRepository conductorRepository;

    public ViajeService(ViajeRepository viajeRepository, ConductorRepository conductorRepository) {
        this.viajeRepository = viajeRepository;
        this.conductorRepository = conductorRepository;
    }

    public List<Viaje> obtenerTodos() {
        return viajeRepository.findAll();
    }

    public Optional<Viaje> obtenerPorId(Long id) {
        return viajeRepository.findById(id);
    }

    public Viaje crearViaje(Long viajeroId) {
        Viaje viaje = new Viaje();
        viaje.setViajeroId(viajeroId);
        viaje.setEstado(ViajeEstado.SOLICITADO);
        return viajeRepository.save(viaje);
    }

    public Viaje cambiarEstado(Long id, ViajeEstado nuevoEstado) {
        return viajeRepository.findById(id)
                .map(viaje -> {
                    if (!esTransicionValida(viaje.getEstado(), nuevoEstado)) {
                        throw new IllegalStateException("Transición no válida de " + viaje.getEstado() + " a " + nuevoEstado);
                    }
                    viaje.setEstado(nuevoEstado);
                    return viajeRepository.save(viaje);
                })
                .orElse(null);
    }

    private boolean esTransicionValida(ViajeEstado actual, ViajeEstado nuevoEstado) {
        return (actual == ViajeEstado.SOLICITADO && nuevoEstado == ViajeEstado.CONDUCTOR_EN_CAMINO)
                || (actual == ViajeEstado.CONDUCTOR_EN_CAMINO && nuevoEstado == ViajeEstado.EN_CURSO)
                || (actual == ViajeEstado.EN_CURSO && nuevoEstado == ViajeEstado.FINALIZADO)
                || (actual == ViajeEstado.SOLICITADO && nuevoEstado == ViajeEstado.CANCELADO)
                || (actual == ViajeEstado.CONDUCTOR_EN_CAMINO && nuevoEstado == ViajeEstado.CANCELADO);
    }
}
