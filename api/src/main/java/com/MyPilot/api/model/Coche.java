package com.MyPilot.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "coches")
public class Coche {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;

    @Column(unique = true)
    private String matricula;

    private Integer anio;
    private LocalDate fechaUltimaItv;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
    public LocalDate getFechaUltimaItv() { return fechaUltimaItv; }
    public void setFechaUltimaItv(LocalDate fechaUltimaItv) { this.fechaUltimaItv = fechaUltimaItv; }
}
