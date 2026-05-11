package com.MyPilot.api.repository;

import com.MyPilot.api.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByViajeId(Long viajeId);

    List<Rating> findAllByViajeIdIn(List<Long> viajeIds);
}

