package com.MyPilot.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long viajeId;

    private Integer puntuacionConductor;

    private Integer puntuacionViajero;

    private LocalDateTime creadoEn;

    @PrePersist
    protected void onCreate() {
        this.creadoEn = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getViajeId() {
        return viajeId;
    }

    public void setViajeId(Long viajeId) {
        this.viajeId = viajeId;
    }

    public Integer getPuntuacionConductor() {
        return puntuacionConductor;
    }

    public void setPuntuacionConductor(Integer puntuacionConductor) {
        this.puntuacionConductor = puntuacionConductor;
    }

    public Integer getPuntuacionViajero() {
        return puntuacionViajero;
    }

    public void setPuntuacionViajero(Integer puntuacionViajero) {
        this.puntuacionViajero = puntuacionViajero;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }
}

