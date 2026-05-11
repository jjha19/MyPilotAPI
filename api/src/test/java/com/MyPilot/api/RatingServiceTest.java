package com.MyPilot.api;

import com.MyPilot.api.model.Rating;
import com.MyPilot.api.model.Viaje;
import com.MyPilot.api.repository.RatingRepository;
import com.MyPilot.api.repository.ViajeRepository;
import com.MyPilot.api.service.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(RatingService.class)
public class RatingServiceTest {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ViajeRepository viajeRepository;

    @Test
    void calculaPromedioIgnorandoNulls() {
        Viaje viaje1 = new Viaje();
        viaje1.setViajeroId(10L);
        viaje1 = viajeRepository.save(viaje1);

        Viaje viaje2 = new Viaje();
        viaje2.setViajeroId(10L);
        viaje2 = viajeRepository.save(viaje2);

        ratingService.registrarPuntuacionViajero(viaje1.getId(), 4);

        Rating ratingSinPuntuacion = new Rating();
        ratingSinPuntuacion.setViajeId(viaje2.getId());
        ratingRepository.save(ratingSinPuntuacion);

        Double promedio = ratingService.calcularPromedioViajero(10L);
        assertEquals(4.0, promedio, 0.0001);
    }

    @Test
    void devuelveCeroSiNoHayRatings() {
        Viaje viaje = new Viaje();
        viaje.setViajeroId(20L);
        viajeRepository.save(viaje);

        Double promedio = ratingService.calcularPromedioViajero(20L);
        assertEquals(0.0, promedio, 0.0001);
    }
}

