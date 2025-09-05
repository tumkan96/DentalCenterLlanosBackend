package com.server.DentalCenterLlanos.Model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios_roles")
public class UsuariosRolesModel {
        @EmbeddedId
        private UsuariosRolesPK Usuarios_RolesPK;
        @ManyToOne
        @MapsId("id_rol")
        @JoinColumn(name = "id_rol")
        RolesModel rol;
        @ManyToOne
        @MapsId("usuario")
        @JoinColumn(name = "usuario")
        UsuariosModel usuario;

        public UsuariosRolesPK getUsuarios_RolesPK() {
                return Usuarios_RolesPK;
        }

        public void setUsuarios_RolesPK(UsuariosRolesPK usuarios_RolesPK) {
                Usuarios_RolesPK = usuarios_RolesPK;
        }

        public RolesModel getRol() {
                return rol;
        }

        public void setRol(RolesModel rol) {
                this.rol = rol;
        }

        public UsuariosModel getUsuario() {
                return usuario;
        }

        public void setUsuario(UsuariosModel usuario) {
                this.usuario = usuario;
        }
}