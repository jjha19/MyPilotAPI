package com.MyPilot.api.repository;

import com.MyPilot.api.model.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    List<Viaje> findAllByViajeroId(Long viajeroId);
    List<Viaje> findAllByConductorId(Long conductorId);
    List<Viaje> findByViajeroIdOrderByCreadoEnDesc(Long viajeroId);
}
