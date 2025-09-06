package com.server.DentalCenterLlanos.Dtos.ModulosDTO;

import com.server.DentalCenterLlanos.Dtos.PermisosDTO.PermisoDTO;
import java.util.List;

public class ModuloDTO {
    private Long idModulo;
    private String nombre;
    private boolean estado;
    private List<PermisoDTO> permisos;
    private List<ModuloDTO> subModulos;

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

    public List<PermisoDTO> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<PermisoDTO> permisos) {
        this.permisos = permisos;
    }

    public List<ModuloDTO> getSubModulos() {
        return subModulos;
    }

    public void setSubModulos(List<ModuloDTO> subModulos) {
        this.subModulos = subModulos;
    }
}
