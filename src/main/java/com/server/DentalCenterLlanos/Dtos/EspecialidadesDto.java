package com.server.DentalCenterLlanos.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadesDto implements Serializable {
    private Long idEspecialidad;
    private String nombre;
    private String descripcion;
    private Long idEspecialidadPadre;
    private boolean requiereInterconsulta;
    private boolean estado;
    private List<OdontologosDto> odontologos = new ArrayList<>();
    private List<TratamientosDto> tratamientos = new ArrayList<>();

    // Getters y Setters
    public Long getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
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

    public Long getIdEspecialidadPadre() {
        return idEspecialidadPadre;
    }

    public void setIdEspecialidadPadre(Long idEspecialidadPadre) {
        this.idEspecialidadPadre = idEspecialidadPadre;
    }

    public boolean isRequiereInterconsulta() {
        return requiereInterconsulta;
    }

    public void setRequiereInterconsulta(boolean requiereInterconsulta) {
        this.requiereInterconsulta = requiereInterconsulta;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @JsonIgnore
    public List<OdontologosDto> getOdontologos() {
        return odontologos;
    }

    public void setOdontologos(List<OdontologosDto> odontologos) {
        this.odontologos = odontologos;
    }

    public List<TratamientosDto> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<TratamientosDto> tratamientos) {
        this.tratamientos = tratamientos;
    }
}