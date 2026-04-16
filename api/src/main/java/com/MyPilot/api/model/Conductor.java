package com.MyPilot.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "conductores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conductor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String correo;

    private Boolean disponible;
    private Double ubicacionLat;
    private Double ubicacionLng;
}
