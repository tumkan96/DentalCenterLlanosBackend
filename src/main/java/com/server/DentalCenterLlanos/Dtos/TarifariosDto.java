package com.server.DentalCenterLlanos.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TarifariosDto implements Serializable {
    private Long idTarifario;
    private String nombre;
    private Date fechaVigencia;
    private boolean estado;
    private List<TarifariosDetalleDto> detalles = new ArrayList<>();

    // Getters y Setters
    public Long getIdTarifario() {
        return idTarifario;
    }

    public void setIdTarifario(Long idTarifario) {
        this.idTarifario = idTarifario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @JsonIgnore
    public List<TarifariosDetalleDto> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<TarifariosDetalleDto> detalles) {
        this.detalles = detalles;
    }
}