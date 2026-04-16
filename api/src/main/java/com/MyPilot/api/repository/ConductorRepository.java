package com.MyPilot.api.repository;

import com.MyPilot.api.model.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConductorRepository extends JpaRepository<Conductor, Long> {
}

