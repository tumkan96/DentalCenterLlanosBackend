package com.server.DentalCenterLlanos.Model;

import java.util.List;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "usuarios")
@JsonIgnoreProperties(value = { "usuariosRoles" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "usuarios")
public class UsuariosModel {

    @Id
    @Column(name = "usuario")
    private String usuarios;

    @Column(name = "contrasena")
    private String contrasena;

    @OneToOne
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona", nullable = false, unique = true)
    private PersonasModel persona;

    @Column(name = "estado")
    private boolean estado;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<UsuariosRolesModel> usuariosRoles;

    public String getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(String usuarios) {
        this.usuarios = usuarios;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public PersonasModel getPersona() {
        return persona;
    }

    public void setPersona(PersonasModel persona) {
        this.persona = persona;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<UsuariosRolesModel> getUsuariosRoles() {
        return usuariosRoles;
    }

    public void setUsuariosRoles(List<UsuariosRolesModel> usuariosRoles) {
        this.usuariosRoles = usuariosRoles;
    }
}
