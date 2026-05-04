package com.MyPilot.api.repository;

import com.MyPilot.api.model.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {
}
