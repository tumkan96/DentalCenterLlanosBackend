package com.server.DentalCenterLlanos.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "submodulos")
public class SubmodulosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_submodulo")
    private Long idSubmodulo;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modulo")
    private ModulosModel modulo;

    public Long getIdSubmodulo() {
        return idSubmodulo;
    }

    public void setIdSubmodulo(Long idSubmodulo) {
        this.idSubmodulo = idSubmodulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public ModulosModel getModulo() {
        return modulo;
    }

    public void setModulo(ModulosModel modulo) {
        this.modulo = modulo;
    }
}
