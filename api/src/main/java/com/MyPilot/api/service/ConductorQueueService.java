package com.MyPilot.api.service;

import com.MyPilot.api.model.Conductor;
import com.MyPilot.api.repository.ConductorRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Service
public class ConductorQueueService {

    private final ConductorRepository repo;
    private final ConcurrentLinkedQueue<ConductorEnCola> cola = new ConcurrentLinkedQueue<>();

    public ConductorQueueService(ConductorRepository repo) {
        this.repo = repo;
    }

    public record ConductorEnCola(
            Long conductorId,
            String nombre,
            String apellido,
            Double ubicacionLat,
            Double ubicacionLng,
            LocalDateTime registradoEn
    ) {
    }

    @PostConstruct
    public void init() {
        List<Conductor> disponibles = repo.findAll().stream()
                .filter(c -> Boolean.TRUE.equals(c.getDisponible()))
                .sorted(Comparator.comparing(Conductor::getId))
                .collect(Collectors.toList());

        disponibles.forEach(this::registrar);
    }

    public void registrar(Conductor conductor) {
        ConductorEnCola item = new ConductorEnCola(
                conductor.getId(),
                conductor.getNombre(),
                conductor.getApellido(),
                conductor.getUbicacionLat(),
                conductor.getUbicacionLng(),
                LocalDateTime.now()
        );
        cola.add(item);
    }

    public void eliminar(Long conductorId) {
        cola.removeIf(c -> c.conductorId().equals(conductorId));
    }

    public List<ConductorEnCola> obtenerPrimeros(int cantidad) {
        return cola.stream()
                .limit(cantidad)
                .collect(Collectors.toList());
    }

    public Optional<ConductorEnCola> obtenerPorId(Long conductorId) {
        return cola.stream()
                .filter(c -> c.conductorId().equals(conductorId))
                .findFirst();
    }
}

