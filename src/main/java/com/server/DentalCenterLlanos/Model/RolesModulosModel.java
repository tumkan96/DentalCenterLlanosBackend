package com.server.DentalCenterLlanos.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles_modulos")
public class RolesModulosModel {

    @EmbeddedId
    private RolesModulosPK rolesModulosPK;

    @ManyToOne
    @MapsId("idRol")
    @JoinColumn(name = "id_rol")
    private RolesModel rol;

    @ManyToOne
    @MapsId("idModulo")
    @JoinColumn(name = "id_modulo")
    private ModulosModel modulo;

    public RolesModulosPK getRolesModulosPK() {
        return rolesModulosPK;
    }

    public void setRolesModulosPK(RolesModulosPK rolesModulosPK) {
        this.rolesModulosPK = rolesModulosPK;
    }

    public RolesModel getRol() {
        return rol;
    }

    public void setRol(RolesModel rol) {
        this.rol = rol;
    }

    public ModulosModel getModulo() {
        return modulo;
    }

    public void setModulo(ModulosModel modulo) {
        this.modulo = modulo;
    }
}
