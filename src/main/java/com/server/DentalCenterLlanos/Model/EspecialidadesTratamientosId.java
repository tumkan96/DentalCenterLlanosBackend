package com.server.DentalCenterLlanos.Model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EspecialidadesTratamientosId implements Serializable {

    private Long idEspecialidad;

    private Long idTratamiento;

    public Long getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public Long getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(Long idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EspecialidadesTratamientosId that = (EspecialidadesTratamientosId) o;
        return Objects.equals(idEspecialidad, that.idEspecialidad) && Objects.equals(idTratamiento, that.idTratamiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEspecialidad, idTratamiento);
    }
}