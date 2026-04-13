package com.MyPilot.api.repository;

import com.MyPilot.api.model.Coche;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocheRepository extends JpaRepository<Coche, Long> {
}
