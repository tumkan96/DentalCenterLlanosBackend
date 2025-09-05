package com.server.DentalCenterLlanos.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "personas")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPersona")
public class PersonasModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "cedula_identidad")
    private String cedulaIdentidad;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @Column(name = "fotografia")
    private String fotografia;

    @Column(name = "telefono_celular")
    private String telefonoCelular;

    @Column(name = "estado")
    private boolean estado;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private UsuariosModel usuario;

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(String cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public UsuariosModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosModel usuario) {
        this.usuario = usuario;
    }
}
