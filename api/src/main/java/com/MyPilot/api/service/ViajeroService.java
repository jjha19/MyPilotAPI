package com.MyPilot.api.service;


import com.MyPilot.api.model.Viajero;
import com.MyPilot.api.repository.ViajeroRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ViajeroService {

    private final ViajeroRepository repo;

    public ViajeroService(ViajeroRepository repo) {
        this.repo = repo;
    }

    public List<Viajero> obtenerTodos() { return repo.findAll(); }

    public Optional<Viajero> obtenerPorId(Long id) { return repo.findById(id); }

    public Viajero guardar(Viajero u) { return repo.save(u); }

    public Viajero actualizar(Long id, Viajero datos) {
        return repo.findById(id)
                .map(viajero -> {
                    viajero.setNombre(datos.getNombre());
                    viajero.setApellido(datos.getApellido());
                    viajero.setCorreo(datos.getCorreo());
                    viajero.setDireccion(datos.getDireccion());
                    viajero.setCantViajes(datos.getCantViajes());
                    viajero.setMatriculaCoche(datos.getMatriculaCoche());
                    return repo.save(viajero);
                })
                .orElse(null);
    }

    public void eliminar(Long id) { repo.deleteById(id); }
}
