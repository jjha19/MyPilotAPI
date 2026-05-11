package com.MyPilot.api.dto;

import com.MyPilot.api.model.Coche;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViajeroDetalleDto {

    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String direccion;
    private Integer cantViajes;
    private Coche coche;
    private Double avgRating;
}

