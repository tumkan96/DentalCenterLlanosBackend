package com.server.DentalCenterLlanos.Dtos.SubmodulosDTO;

public class SubmoduloDTO {
    private Long idSubmodulo;
    private String nombre;
    private boolean estado;

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
}
