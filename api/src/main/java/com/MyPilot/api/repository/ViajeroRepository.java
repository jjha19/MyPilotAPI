package com.MyPilot.api.repository;

import com.MyPilot.api.model.Viajero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViajeroRepository extends JpaRepository<Viajero, Long> {
    // Spring genera las consultas básicas automáticamente ✨
    // findAll(), findById(), save(), deleteById()...
}
