package com.server.DentalCenterLlanos.Model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "permisos")
public class PermisosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    private Long idPermiso;

    @Column(name = "accion", nullable = false)
    private String accion;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "id_modulo")
    @JsonBackReference
    private ModulosModel modulo;

    @OneToMany(mappedBy = "permiso", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ModulosPermisosModel> modulosPermisos;

    public Long getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Long idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
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

    public List<ModulosPermisosModel> getModulosPermisos() {
        return modulosPermisos;
    }

    public void setModulosPermisos(List<ModulosPermisosModel> modulosPermisos) {
        this.modulosPermisos = modulosPermisos;
    }
}
