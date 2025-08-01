package com.server.DentalCenterLlanos.Model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
@JsonIgnoreProperties(value = { "usuariosRoles" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "usuarios")
public class UsuariosModel {
    @Id
    @Column(name = "usuarios")
    private String usuarios;

    @Column(name = "contrasena")
    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    private PersonasModel persona;

	    @Column(name = "cod_estado")
	    private int cod_estado;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonManagedReference
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Usuarios_RolesModel> usuariosRoles;

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

    public int getCod_estado() {
        return cod_estado;
    }

    public void setCod_estado(int cod_estado) {
        this.cod_estado = cod_estado;
    }

    public PersonasModel getPersona() {
        return persona;
    }

    public void setPersona(PersonasModel persona) {
        this.persona = persona;
    }

    public List<Usuarios_RolesModel> getUsuariosRoles() {
        return usuariosRoles;
    }

    public void setUsuariosRoles(List<Usuarios_RolesModel> usuariosRoles) {
        this.usuariosRoles = usuariosRoles;
    }
}