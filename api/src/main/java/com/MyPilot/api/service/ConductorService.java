package com.MyPilot.api.service;

import com.MyPilot.api.model.Conductor;
import com.MyPilot.api.repository.ConductorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConductorService {

    private final ConductorRepository repo;

    public ConductorService(ConductorRepository repo) {
        this.repo = repo;
    }

    public List<Conductor> obtenerTodos() {
        return repo.findAll();
    }

    public Optional<Conductor> obtenerPorId(Long id) {
        return repo.findById(id);
    }

    public Conductor guardar(Conductor conductor) {
        return repo.save(conductor);
    }

    public Conductor actualizar(Long id, Conductor datos) {
        return repo.findById(id)
                .map(conductor -> {
                    conductor.setNombre(datos.getNombre());
                    conductor.setApellido(datos.getApellido());
                    conductor.setCorreo(datos.getCorreo());
                    conductor.setDisponible(datos.getDisponible());
                    conductor.setUbicacionLat(datos.getUbicacionLat());
                    conductor.setUbicacionLng(datos.getUbicacionLng());
                    return repo.save(conductor);
                })
                .orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public Conductor actualizarUbicacion(Long id, Double lat, Double lng) {
        return repo.findById(id)
                .map(conductor -> {
                    conductor.setUbicacionLat(lat);
                    conductor.setUbicacionLng(lng);
                    return repo.save(conductor);
                })
                .orElse(null);
    }
}
