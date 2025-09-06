package com.server.DentalCenterLlanos.Dtos.UsuariosDTO;

import com.server.DentalCenterLlanos.Dtos.Personas.PersonaDTO;
import com.server.DentalCenterLlanos.Dtos.UsuarioRolDTO.UsuarioRolDTO;

import java.util.List;

public class UsuarioDTO {
    private String usuarios;
    private boolean estado;
    private PersonaDTO persona;
    private List<UsuarioRolDTO> usuariosRoles;

    public String getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(String usuarios) {
        this.usuarios = usuarios;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public PersonaDTO getPersona() {
        return persona;
    }

    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }

    public List<UsuarioRolDTO> getUsuariosRoles() {
        return usuariosRoles;
    }

    public void setUsuariosRoles(List<UsuarioRolDTO> usuariosRoles) {
        this.usuariosRoles = usuariosRoles;
    }
}
