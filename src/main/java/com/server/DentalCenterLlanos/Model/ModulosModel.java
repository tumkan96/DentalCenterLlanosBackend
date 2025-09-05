package com.server.DentalCenterLlanos.Model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "modulos")
public class ModulosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modulo")
    private Long idModulo;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    @OneToMany(mappedBy = "modulo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<PermisosModel> permisos;

    // Relación con submódulos (auto-relación)
    @OneToMany(mappedBy = "parentModulo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ModulosModel> subModulos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id_modulo")
    @JsonBackReference
    private ModulosModel parentModulo;

    public Long getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
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

    public List<PermisosModel> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<PermisosModel> permisos) {
        this.permisos = permisos;
    }

    public List<ModulosModel> getSubModulos() {
        return subModulos;
    }

    public void setSubModulos(List<ModulosModel> subModulos) {
        this.subModulos = subModulos;
    }

    public ModulosModel getParentModulo() {
        return parentModulo;
    }

    public void setParentModulo(ModulosModel parentModulo) {
        this.parentModulo = parentModulo;
    }
}
