package com.server.DentalCenterLlanos.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "especialidades")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idEspecialidad")
public class EspecialidadesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialidad")
    private Long idEspecialidad;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_especialidad_padre")
    private EspecialidadesModel especialidadPadre;

    @Column(name = "requiere_interconsulta")
    private boolean requiereInterconsulta = false;

    @Column(name = "estado")
    private boolean estado = true;

    @OneToMany(mappedBy = "especialidad", cascade = CascadeType.ALL)
    private Set<OdontologosEspecialidadesModel> odontologosAsociados = new HashSet<>();

    @OneToMany(mappedBy = "especialidad", cascade = CascadeType.ALL)
    private Set<EspecialidadesTratamientosModel> tratamientosAsociados = new HashSet<>();

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

    public EspecialidadesModel getEspecialidadPadre() {
        return especialidadPadre;
    }

    public void setEspecialidadPadre(EspecialidadesModel especialidadPadre) {
        this.especialidadPadre = especialidadPadre;
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

    public Set<OdontologosEspecialidadesModel> getOdontologosAsociados() {
        return odontologosAsociados;
    }

    public void setOdontologosAsociados(Set<OdontologosEspecialidadesModel> odontologosAsociados) {
        this.odontologosAsociados = odontologosAsociados;
    }

    public Set<EspecialidadesTratamientosModel> getTratamientosAsociados() {
        return tratamientosAsociados;
    }

    public void setTratamientosAsociados(Set<EspecialidadesTratamientosModel> tratamientosAsociados) {
        this.tratamientosAsociados = tratamientosAsociados;
    }
}