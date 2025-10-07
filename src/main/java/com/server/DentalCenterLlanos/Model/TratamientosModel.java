package com.server.DentalCenterLlanos.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tratamientos")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idTratamiento")
public class TratamientosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tratamiento")
    private Long idTratamiento;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "codigo_cups_cie10")
    private String codigoCupsCie10;

    // @Column(name = "tiempo_estimado_minutos")
    // private Integer tiempoEstimadoMinutos;

    @Column(name = "estado")
    private boolean estado = true;

    @OneToMany(mappedBy = "tratamiento", cascade = CascadeType.ALL)
    private Set<EspecialidadesTratamientosModel> especialidadesAsociadas = new HashSet<>();

    @OneToMany(mappedBy = "tratamiento", cascade = CascadeType.ALL)
    private Set<TarifariosDetalleModel> tarifariosDetalles = new HashSet<>();

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

    // public Integer getTiempoEstimadoMinutos() {
    //     return tiempoEstimadoMinutos;
    // }

    // public void setTiempoEstimadoMinutos(Integer tiempoEstimadoMinutos) {
    //     this.tiempoEstimadoMinutos = tiempoEstimadoMinutos;
    // }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Set<EspecialidadesTratamientosModel> getEspecialidadesAsociadas() {
        return especialidadesAsociadas;
    }

    public void setEspecialidadesAsociadas(Set<EspecialidadesTratamientosModel> especialidadesAsociadas) {
        this.especialidadesAsociadas = especialidadesAsociadas;
    }

    public Set<TarifariosDetalleModel> getTarifariosDetalles() {
        return tarifariosDetalles;
    }

    public void setTarifariosDetalles(Set<TarifariosDetalleModel> tarifariosDetalles) {
        this.tarifariosDetalles = tarifariosDetalles;
    }
}