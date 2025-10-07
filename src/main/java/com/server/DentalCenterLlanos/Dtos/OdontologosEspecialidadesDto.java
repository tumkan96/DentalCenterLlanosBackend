package com.server.DentalCenterLlanos.Dtos;

import com.server.DentalCenterLlanos.Model.OdontologosEspecialidadesId;
import java.io.Serializable;

public class OdontologosEspecialidadesDto implements Serializable {
    private OdontologosEspecialidadesId id;
    private OdontologosDto odontologo;
    private EspecialidadesDto especialidad;

    // Getters y Setters
    public OdontologosEspecialidadesId getId() {
        return id;
    }

    public void setId(OdontologosEspecialidadesId id) {
        this.id = id;
    }

    public OdontologosDto getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(OdontologosDto odontologo) {
        this.odontologo = odontologo;
    }

    public EspecialidadesDto getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(EspecialidadesDto especialidad) {
        this.especialidad = especialidad;
    }
}