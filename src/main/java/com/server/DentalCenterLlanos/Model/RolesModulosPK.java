package com.server.DentalCenterLlanos.Model;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RolesModulosPK implements Serializable {

    @Column(name = "id_rol")
    private Long idRol;

    @Column(name = "id_modulo")
    private Long idModulo;

    public RolesModulosPK() {}

    public RolesModulosPK(Long idRol, Long idModulo) {
        this.idRol = idRol;
        this.idModulo = idModulo;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public Long getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RolesModulosPK)) return false;
        RolesModulosPK that = (RolesModulosPK) o;
        return Objects.equals(idRol, that.idRol) &&
               Objects.equals(idModulo, that.idModulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRol, idModulo);
    }
}
