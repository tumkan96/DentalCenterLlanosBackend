package com.server.DentalCenterLlanos.Model;

import java.io.Serializable;
import java.util.Objects;

public class UsuariosRolesPK implements Serializable {
	protected String usuario;
	protected Long id_rol;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Long getId_rol() {
		return id_rol;
	}

	public void setId_rol(Long id_rol) {
		this.id_rol = id_rol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_rol, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuariosRolesPK other = (UsuariosRolesPK) obj;
		return id_rol == other.id_rol && Objects.equals(usuario, other.usuario);
	}

}
