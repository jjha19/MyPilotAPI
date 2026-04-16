package com.MyPilot.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "viajeros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Viajero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String correo;

    private String direccion;
    private Integer cantViajes;
    private String matriculaCoche;
}