package com.server.DentalCenterLlanos.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "odontologos")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPersona")
public class OdontologosModel {

    @Id
    @Column(name = "id_persona")
    private Long idPersona;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id_persona")
    private PersonasModel persona;

    @Column(name = "num_colegiatura")
    private String numColegiatura;

    @Column(name = "calificacion_promedio")
    private double calificacionPromedio = 0.0;

    @Column(name = "estado")
    private boolean estado = true;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "direccion_domicilio")
    private String direccionDomicilio;

    @OneToMany(mappedBy = "odontologo", cascade = CascadeType.ALL)
    private Set<OdontologosEspecialidadesModel> especialidadesAsociadas = new HashSet<>();

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public PersonasModel getPersona() {
        return persona;
    }

    public void setPersona(PersonasModel persona) {
        this.persona = persona;
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

    public Set<OdontologosEspecialidadesModel> getEspecialidadesAsociadas() {
        return especialidadesAsociadas;
    }

    public void setEspecialidadesAsociadas(Set<OdontologosEspecialidadesModel> especialidadesAsociadas) {
        this.especialidadesAsociadas = especialidadesAsociadas;
    }
}