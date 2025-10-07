package com.server.DentalCenterLlanos.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TratamientosDto implements Serializable {
    private Long idTratamiento;
    private String nombre;
    private String descripcion;
    private String codigoCupsCie10;
    private boolean estado;
    private List<EspecialidadesDto> especialidades = new ArrayList<>();
    private List<TarifariosDetalleDto> tarifariosDetalles = new ArrayList<>();

    // Getters y Setters
    public Long getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(Long idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoCupsCie10() {
        return codigoCupsCie10;
    }

    public void setCodigoCupsCie10(String codigoCupsCie10) {
        this.codigoCupsCie10 = codigoCupsCie10;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @JsonIgnore
    public List<EspecialidadesDto> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<EspecialidadesDto> especialidades) {
        this.especialidades = especialidades;
    }

    public List<TarifariosDetalleDto> getTarifariosDetalles() {
        return tarifariosDetalles;
    }

    public void setTarifariosDetalles(List<TarifariosDetalleDto> tarifariosDetalles) {
        this.tarifariosDetalles = tarifariosDetalles;
    }
}