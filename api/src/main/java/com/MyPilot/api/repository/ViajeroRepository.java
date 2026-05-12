package com.MyPilot.api.repository;

import com.MyPilot.api.model.Viajero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViajeroRepository extends JpaRepository<Viajero, Long> {
    // Spring genera las consultas básicas automáticamente ✨
    // findAll(), findById(), save(), deleteById()...
    Optional<Viajero> findByCorreo(String correo);
}
