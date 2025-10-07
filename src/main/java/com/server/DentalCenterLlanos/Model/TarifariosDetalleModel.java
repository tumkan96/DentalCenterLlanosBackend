package com.server.DentalCenterLlanos.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "tarifarios_detalle")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idTarifarioDetalle")
public class TarifariosDetalleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarifario_detalle")
    private Long idTarifarioDetalle;

    @ManyToOne
    @JoinColumn(name = "id_tarifario")
    private TarifariosModel tarifario;

    @ManyToOne
    @JoinColumn(name = "id_tratamiento")
    private TratamientosModel tratamiento;

    @Column(name = "precio")
    private double precio;

    public Long getIdTarifarioDetalle() {
        return idTarifarioDetalle;
    }

    public void setIdTarifarioDetalle(Long idTarifarioDetalle) {
        this.idTarifarioDetalle = idTarifarioDetalle;
    }

    public TarifariosModel getTarifario() {
        return tarifario;
    }

    public void setTarifario(TarifariosModel tarifario) {
        this.tarifario = tarifario;
    }

    public TratamientosModel getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(TratamientosModel tratamiento) {
        this.tratamiento = tratamiento;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}