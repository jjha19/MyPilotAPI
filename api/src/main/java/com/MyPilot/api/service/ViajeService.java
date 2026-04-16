package com.MyPilot.api.service;

import com.MyPilot.api.model.Conductor;
import com.MyPilot.api.model.EstadoViaje;
import com.MyPilot.api.model.Viaje;
import com.MyPilot.api.repository.ConductorRepository;
import com.MyPilot.api.repository.ViajeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Viaje guardar(Viaje viaje) {
        if (viaje.getEstado() == null) {
            viaje.setEstado(EstadoViaje.PENDIENTE);
        }
        if (viaje.getFechaSolicitud() == null) {
            viaje.setFechaSolicitud(LocalDateTime.now());
        }
        return viajeRepository.save(viaje);
    }

    public List<Viaje> obtenerPendientes() {
        return viajeRepository.findByEstado(EstadoViaje.PENDIENTE);
    }

    public Viaje aceptarViaje(Long viajeId, Long conductorId) {
        Viaje viaje = viajeRepository.findById(viajeId).orElse(null);
        if (viaje == null) {
            return null;
        }
        Conductor conductor = conductorRepository.findById(conductorId).orElse(null);
        if (conductor == null) {
            return null;
        }
        viaje.setConductor(conductor);
        viaje.setEstado(EstadoViaje.ACEPTADO);
        conductor.setDisponible(false);
        conductorRepository.save(conductor);
        return viajeRepository.save(viaje);
    }

    public Viaje rechazarViaje(Long viajeId) {
        return actualizarEstado(viajeId, EstadoViaje.CANCELADO);
    }

    public Viaje actualizarEstado(Long id, EstadoViaje estado) {
        return viajeRepository.findById(id)
                .map(viaje -> {
                    viaje.setEstado(estado);
                    return viajeRepository.save(viaje);
                })
                .orElse(null);
    }
}

