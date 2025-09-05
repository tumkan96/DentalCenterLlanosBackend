package com.server.DentalCenterLlanos.Model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ModulosPermisosPK implements Serializable {

    @Column(name = "id_modulo")
    private Long idModulo;

    @Column(name = "id_permiso")
    private Long idPermiso;

    public Long getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
    }

    public Long getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Long idPermiso) {
        this.idPermiso = idPermiso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModulosPermisosPK)) return false;
        ModulosPermisosPK that = (ModulosPermisosPK) o;
        return Objects.equals(idModulo, that.idModulo) && Objects.equals(idPermiso, that.idPermiso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idModulo, idPermiso);
    }
}
