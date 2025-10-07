package com.server.DentalCenterLlanos.Dtos;

import java.io.Serializable;

public class TarifariosDetalleDto implements Serializable {
    private Long idTarifarioDetalle;
    private TarifariosDto tarifario;
    private TratamientosDto tratamiento;
    private double precio;

    // Getters y Setters
    public Long getIdTarifarioDetalle() {
        return idTarifarioDetalle;
    }

    public void setIdTarifarioDetalle(Long idTarifarioDetalle) {
        this.idTarifarioDetalle = idTarifarioDetalle;
    }

    public TarifariosDto getTarifario() {
        return tarifario;
    }

    public void setTarifario(TarifariosDto tarifario) {
        this.tarifario = tarifario;
    }

    public TratamientosDto getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(TratamientosDto tratamiento) {
        this.tratamiento = tratamiento;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}