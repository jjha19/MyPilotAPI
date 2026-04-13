package com.MyPilot.api.service;

import com.MyPilot.api.model.Coche;
import com.MyPilot.api.repository.CocheRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CocheService {

    private final CocheRepository repo;

    public CocheService(CocheRepository repo) {
        this.repo = repo;
    }

    public List<Coche> obtenerTodos() { return repo.findAll(); }

    public Optional<Coche> obtenerPorId(Long id) { return repo.findById(id); }

    public Coche guardar(Coche coche) { return repo.save(coche); }

    public Coche actualizar(Long id, Coche datos) {
        return repo.findById(id)
                .map(coche -> {
                    coche.setMarca(datos.getMarca());
                    coche.setModelo(datos.getModelo());
                    coche.setMatricula(datos.getMatricula());
                    coche.setAnio(datos.getAnio());
                    coche.setFechaUltimaItv(datos.getFechaUltimaItv());
                    return repo.save(coche);
                })
                .orElse(null);
    }

    public void eliminar(Long id) { repo.deleteById(id); }
}
