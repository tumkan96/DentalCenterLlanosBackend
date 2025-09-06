package com.server.DentalCenterLlanos.Dtos.RolesModulosDTO;

import com.server.DentalCenterLlanos.Dtos.Roles.RolDTO;
import com.server.DentalCenterLlanos.Dtos.ModulosDTO.ModuloDTO;

public class RolModuloDTO {
    private RolDTO rol;
    private ModuloDTO modulo;

    public RolDTO getRol() {
        return rol;
    }

    public void setRol(RolDTO rol) {
        this.rol = rol;
    }

    public ModuloDTO getModulo() {
        return modulo;
    }

    public void setModulo(ModuloDTO modulo) {
        this.modulo = modulo;
    }
}
