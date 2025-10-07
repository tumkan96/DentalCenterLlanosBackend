package com.server.DentalCenterLlanos.Dtos;


import com.server.DentalCenterLlanos.Dtos.Personas.PersonaDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OdontologosDto implements Serializable {
    private Long idPersona;
    private String numColegiatura;
    private double calificacionPromedio;
    private boolean estado;
    private String correoElectronico;
    private String direccionDomicilio;
    private List<EspecialidadesDto> especialidades = new ArrayList<>();
    private PersonaDTO persona;

    // Getters y Setters
    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getNumColegiatura() {
        return numColegiatura;
    }

    public void setNumColegiatura(String numColegiatura) {
        this.numColegiatura = numColegiatura;
    }

    public double getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public void setCalificacionPromedio(double calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccionDomicilio() {
        return direccionDomicilio;
    }

    public void setDireccionDomicilio(String direccionDomicilio) {
        this.direccionDomicilio = direccionDomicilio;
    }

    public List<EspecialidadesDto> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<EspecialidadesDto> especialidades) {
        this.especialidades = especialidades;
    }

    public PersonaDTO getPersona() {
        return persona;
    }

    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }
}