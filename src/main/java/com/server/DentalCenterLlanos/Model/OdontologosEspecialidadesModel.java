package com.server.DentalCenterLlanos.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "odontologos_especialidades")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OdontologosEspecialidadesModel {

    @EmbeddedId
    private OdontologosEspecialidadesId id = new OdontologosEspecialidadesId();

    @ManyToOne
    @MapsId("idOdontologo")
    @JoinColumn(name = "id_odontologo", referencedColumnName = "id_persona")
    private OdontologosModel odontologo;

    @ManyToOne
    @MapsId("idEspecialidad")
    @JoinColumn(name = "id_especialidad")
    private EspecialidadesModel especialidad;

    public OdontologosEspecialidadesId getId() {
        return id;
    }

    public void setId(OdontologosEspecialidadesId id) {
        this.id = id;
    }

    public OdontologosModel getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(OdontologosModel odontologo) {
        this.odontologo = odontologo;
    }

    public EspecialidadesModel getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(EspecialidadesModel especialidad) {
        this.especialidad = especialidad;
    }
}