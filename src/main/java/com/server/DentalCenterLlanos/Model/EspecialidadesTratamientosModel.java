package com.server.DentalCenterLlanos.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "especialidades_tratamientos")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class EspecialidadesTratamientosModel {

    @EmbeddedId
    private EspecialidadesTratamientosId id = new EspecialidadesTratamientosId();

    @ManyToOne
    @MapsId("idEspecialidad")
    @JoinColumn(name = "id_especialidad")
    private EspecialidadesModel especialidad;

    @ManyToOne
    @MapsId("idTratamiento")
    @JoinColumn(name = "id_tratamiento")
    private TratamientosModel tratamiento;

    public EspecialidadesTratamientosId getId() {
        return id;
    }

    public void setId(EspecialidadesTratamientosId id) {
        this.id = id;
    }

    public EspecialidadesModel getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(EspecialidadesModel especialidad) {
        this.especialidad = especialidad;
    }

    public TratamientosModel getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(TratamientosModel tratamiento) {
        this.tratamiento = tratamiento;
    }
}