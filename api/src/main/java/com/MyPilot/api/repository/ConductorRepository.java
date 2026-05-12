package com.MyPilot.api.repository;

import com.MyPilot.api.model.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConductorRepository extends JpaRepository<Conductor, Long> {
    Optional<Conductor> findByCorreo(String correo);
}
