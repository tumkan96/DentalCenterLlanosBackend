package com.server.DentalCenterLlanos.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "personas")
public class PersonasModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_persona")
	long id_persona;

	@Column(name = "cedula_identidad")
	String cedula_identidad;

	@Column(name = "nombres")
	String nombres;

	@Column(name = "apellido_paterno")
	String apellido_paterno;

	@Column(name = "apellido_materno")
	String apellido_materno;

	@Column(name = "fotografia")
	String fotografia;

	@Column(name = "telefono_celular")
	String telefono_celular;

	@Column(name = "cod_estado")
	int cod_estado;

	public long getId_persona() {
		return id_persona;
	}

	public void setId_persona(long id_persona) {
		this.id_persona = id_persona;
	}

	public String getCedula_identidad() {
		return cedula_identidad;
	}

	public void setCedula_identidad(String cedula_identidad) {
		this.cedula_identidad = cedula_identidad;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellido_paterno() {
		return apellido_paterno;
	}

	public void setApellido_paterno(String apellido_paterno) {
		this.apellido_paterno = apellido_paterno;
	}

	public String getApellido_materno() {
		return apellido_materno;
	}

	public void setApellido_materno(String apellido_materno) {
		this.apellido_materno = apellido_materno;
	}

	public String getFotografia() {
		return fotografia;
	}

	public void setFotografia(String fotografia) {
		this.fotografia = fotografia;
	}

	public String getTelefono_celular() {
		return telefono_celular;
	}

	public void setTelefono_celular(String telefono_celular) {
		this.telefono_celular = telefono_celular;
	}

	public int getCod_estado() {
		return cod_estado;
	}

	public void setCod_estado(int cod_estado) {
		this.cod_estado = cod_estado;
	}

}
