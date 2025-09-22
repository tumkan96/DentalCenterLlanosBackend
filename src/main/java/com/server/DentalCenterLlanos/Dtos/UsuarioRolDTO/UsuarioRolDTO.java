package com.server.DentalCenterLlanos.Dtos.UsuarioRolDTO;

import com.server.DentalCenterLlanos.Dtos.Roles.RolDTO;

public class UsuarioRolDTO {

    private String usuario; // ID del usuario
    private RolDTO rol;     // Información del rol

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public RolDTO getRol() {
        return rol;
    }

    public void setRol(RolDTO rol) {
        this.rol = rol;
    }
}
