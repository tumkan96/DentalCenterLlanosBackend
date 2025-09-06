package com.server.DentalCenterLlanos.Dtos.Roles;

import com.server.DentalCenterLlanos.Dtos.ModulosDTO.ModuloDTO;
import java.util.List;

public class RolDTO {
    private Long idRol;
    private String nombre;
    private String descripcion;
    private boolean estado;
    private List<ModuloDTO> modulos;

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<ModuloDTO> getModulos() {
        return modulos;
    }

    public void setModulos(List<ModuloDTO> modulos) {
        this.modulos = modulos;
    }
}
