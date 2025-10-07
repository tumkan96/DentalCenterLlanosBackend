package com.server.DentalCenterLlanos.Dtos;

import com.server.DentalCenterLlanos.Model.EspecialidadesTratamientosId;
import java.io.Serializable;

public class EspecialidadesTratamientosDto implements Serializable {
    private EspecialidadesTratamientosId id;
    private EspecialidadesDto especialidad;
    private TratamientosDto tratamiento;

    // Getters y Setters
    public EspecialidadesTratamientosId getId() {
        return id;
    }

    public void setId(EspecialidadesTratamientosId id) {
        this.id = id;
    }

    public EspecialidadesDto getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(EspecialidadesDto especialidad) {
        this.especialidad = especialidad;
    }

    public TratamientosDto getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(TratamientosDto tratamiento) {
        this.tratamiento = tratamiento;
    }
}