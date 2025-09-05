package com.server.DentalCenterLlanos.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "modulos_permisos")
public class ModulosPermisosModel {

    @EmbeddedId
    private ModulosPermisosPK modulosPermisosPK;

    @ManyToOne
    @MapsId("idModulo")
    @JoinColumn(name = "id_modulo")
    private ModulosModel modulo;

    @ManyToOne
    @MapsId("idPermiso")
    @JoinColumn(name = "id_permiso")
    private PermisosModel permiso;

    public ModulosPermisosPK getModulosPermisosPK() {
        return modulosPermisosPK;
    }

    public void setModulosPermisosPK(ModulosPermisosPK modulosPermisosPK) {
        this.modulosPermisosPK = modulosPermisosPK;
    }

    public ModulosModel getModulo() {
        return modulo;
    }

    public void setModulo(ModulosModel modulo) {
        this.modulo = modulo;
    }

    public PermisosModel getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisosModel permiso) {
        this.permiso = permiso;
    }
}
