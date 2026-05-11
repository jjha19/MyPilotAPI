package com.MyPilot.api.service;

import com.MyPilot.api.model.Rating;
import com.MyPilot.api.model.Viaje;
import com.MyPilot.api.repository.RatingRepository;
import com.MyPilot.api.repository.ViajeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final ViajeRepository viajeRepository;

    public RatingService(RatingRepository ratingRepository, ViajeRepository viajeRepository) {
        this.ratingRepository = ratingRepository;
        this.viajeRepository = viajeRepository;
    }

    public Rating registrarPuntuacionConductor(Long viajeId, Integer puntuacion) {
        Rating rating = ratingRepository.findByViajeId(viajeId)
                .orElseGet(() -> {
                    Rating nuevo = new Rating();
                    nuevo.setViajeId(viajeId);
                    return nuevo;
                });
        rating.setPuntuacionConductor(puntuacion);
        return ratingRepository.save(rating);
    }

    public Rating registrarPuntuacionViajero(Long viajeId, Integer puntuacion) {
        Rating rating = ratingRepository.findByViajeId(viajeId)
                .orElseGet(() -> {
                    Rating nuevo = new Rating();
                    nuevo.setViajeId(viajeId);
                    return nuevo;
                });
        rating.setPuntuacionViajero(puntuacion);
        return ratingRepository.save(rating);
    }

    public Double calcularPromedioViajero(Long viajeroId) {
        List<Viaje> viajes = viajeRepository.findAllByViajeroId(viajeroId);
        if (viajes.isEmpty()) {
            return 0.0;
        }

        List<Long> viajeIds = viajes.stream()
                .map(Viaje::getId)
                .collect(Collectors.toList());

        if (viajeIds.isEmpty()) {
            return 0.0;
        }

        List<Rating> ratings = ratingRepository.findAllByViajeIdIn(viajeIds);

        return ratings.stream()
                .map(Rating::getPuntuacionViajero)
                .filter(puntuacion -> puntuacion != null)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    public Double calcularPromedioConductor(Long conductorId) {
        List<Viaje> viajes = viajeRepository.findAllByConductorId(conductorId);
        if (viajes.isEmpty()) {
            return 0.0;
        }

        List<Long> viajeIds = viajes.stream()
                .map(Viaje::getId)
                .collect(Collectors.toList());

        if (viajeIds.isEmpty()) {
            return 0.0;
        }

        List<Rating> ratings = ratingRepository.findAllByViajeIdIn(viajeIds);

        return ratings.stream()
                .map(Rating::getPuntuacionConductor)
                .filter(puntuacion -> puntuacion != null)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }
}
