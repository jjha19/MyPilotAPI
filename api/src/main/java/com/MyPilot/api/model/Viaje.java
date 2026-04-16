package com.MyPilot.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "viajes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double origenLat;
    private Double origenLng;
    private Double destinoLat;
    private Double destinoLng;

    @Enumerated(EnumType.STRING)
    private EstadoViaje estado;

    private LocalDateTime fechaSolicitud;
    private Double precio;

    @ManyToOne(optional = false)
    @JoinColumn(name = "viajero_id", nullable = false)
    private Viajero viajero;

    @ManyToOne
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;
}

