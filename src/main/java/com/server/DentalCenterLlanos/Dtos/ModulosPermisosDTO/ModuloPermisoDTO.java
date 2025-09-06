package com.server.DentalCenterLlanos.Dtos.ModulosPermisosDTO;

import com.server.DentalCenterLlanos.Dtos.ModulosDTO.ModuloDTO;
import com.server.DentalCenterLlanos.Dtos.PermisosDTO.PermisoDTO;

public class ModuloPermisoDTO {
    private ModuloDTO modulo;
    private PermisoDTO permiso;

    public ModuloDTO getModulo() {
        return modulo;
    }

    public void setModulo(ModuloDTO modulo) {
        this.modulo = modulo;
    }

    public PermisoDTO getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoDTO permiso) {
        this.permiso = permiso;
    }
}
