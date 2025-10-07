package com.server.DentalCenterLlanos.Dtos.UsuarioRolDTO;

import com.server.DentalCenterLlanos.Model.PersonasModel;
import com.server.DentalCenterLlanos.Model.RolesModel;

public class UsuarioRolPersonaDTO {

    private String usuario;
    private PersonasModel persona;
    private RolesModel rol;

    public UsuarioRolPersonaDTO(String usuario, PersonasModel persona, RolesModel rol) {
        this.usuario = usuario;
        this.persona = persona;
        this.rol = rol;
    }

    // Getters y Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public PersonasModel getPersona() {
        return persona;
    }

    public void setPersona(PersonasModel persona) {
        this.persona = persona;
    }

    public RolesModel getRol() {
        return rol;
    }

    public void setRol(RolesModel rol) {
        this.rol = rol;
    }
}
