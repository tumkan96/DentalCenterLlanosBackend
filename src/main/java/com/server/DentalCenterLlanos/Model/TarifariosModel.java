package com.server.DentalCenterLlanos.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tarifarios")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idTarifario")
public class TarifariosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarifario")
    private Long idTarifario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecha_vigencia")
    private Date fechaVigencia;

    @Column(name = "estado")
    private boolean estado = true;

    @OneToMany(mappedBy = "tarifario", cascade = CascadeType.ALL)
    private Set<TarifariosDetalleModel> detalles = new HashSet<>();

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

    public Set<TarifariosDetalleModel> getDetalles() {
        return detalles;
    }

    public void setDetalles(Set<TarifariosDetalleModel> detalles) {
        this.detalles = detalles;
    }
}