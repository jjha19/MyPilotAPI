package com.MyPilot.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "viajes")
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long viajeroId;

    private Long conductorId;

    @Enumerated(EnumType.STRING)
    private ViajeEstado estado = ViajeEstado.SOLICITADO;

    private LocalDateTime creadoEn;

    private LocalDateTime actualizadoEn;

    private String origenDireccion;

    private Double origenLat;

    private Double origenLng;

    private String destinoDireccion;

    private Double destinoLat;

    private Double destinoLng;

    @PrePersist
    public void prePersist() {
        if (this.estado == null) {
            this.estado = ViajeEstado.SOLICITADO;
        }
        this.creadoEn = LocalDateTime.now();
        this.actualizadoEn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.actualizadoEn = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getViajeroId() {
        return viajeroId;
    }

    public void setViajeroId(Long viajeroId) {
        this.viajeroId = viajeroId;
    }

    public Long getConductorId() {
        return conductorId;
    }

    public void setConductorId(Long conductorId) {
        this.conductorId = conductorId;
    }

    public ViajeEstado getEstado() {
        return estado;
    }

    public void setEstado(ViajeEstado estado) {
        this.estado = estado;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public String getOrigenDireccion() {
        return origenDireccion;
    }

    public void setOrigenDireccion(String origenDireccion) {
        this.origenDireccion = origenDireccion;
    }

    public Double getOrigenLat() {
        return origenLat;
    }

    public void setOrigenLat(Double origenLat) {
        this.origenLat = origenLat;
    }

    public Double getOrigenLng() {
        return origenLng;
    }

    public void setOrigenLng(Double origenLng) {
        this.origenLng = origenLng;
    }

    public String getDestinoDireccion() {
        return destinoDireccion;
    }

    public void setDestinoDireccion(String destinoDireccion) {
        this.destinoDireccion = destinoDireccion;
    }

    public Double getDestinoLat() {
        return destinoLat;
    }

    public void setDestinoLat(Double destinoLat) {
        this.destinoLat = destinoLat;
    }

    public Double getDestinoLng() {
        return destinoLng;
    }

    public void setDestinoLng(Double destinoLng) {
        this.destinoLng = destinoLng;
    }
}
