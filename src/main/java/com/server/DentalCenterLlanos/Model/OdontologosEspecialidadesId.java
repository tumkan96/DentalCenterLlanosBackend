package com.server.DentalCenterLlanos.Model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OdontologosEspecialidadesId implements Serializable {

    private Long idOdontologo;

    private Long idEspecialidad;

    public Long getIdOdontologo() {
        return idOdontologo;
    }

    public void setIdOdontologo(Long idOdontologo) {
        this.idOdontologo = idOdontologo;
    }

    public Long getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OdontologosEspecialidadesId that = (OdontologosEspecialidadesId) o;
        return Objects.equals(idOdontologo, that.idOdontologo) && Objects.equals(idEspecialidad, that.idEspecialidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOdontologo, idEspecialidad);
    }
}