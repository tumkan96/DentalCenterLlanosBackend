package com.server.DentalCenterLlanos.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.DentalCenterLlanos.Model.UsuariosRolesModel;
import com.server.DentalCenterLlanos.Repository.UsuariosRolesRepository;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = "*")
@RestController
public class UsuariosRolesController {

    @Autowired
    public UsuariosRolesRepository usuariosRolesRepository;

    @GetMapping("/api/listausuarioRoles")
    public List<UsuariosRolesModel> listaUsuarioRol() {
        return usuariosRolesRepository.findAll();
    }

    @Transactional
    @PostMapping("/api/asignarUsuarioRol/{usu}/{rol}")
    public boolean asignarUsuarioRol(@PathVariable String usu, @PathVariable Long rol) {
        usuariosRolesRepository.addUsuarioRol(usu, rol);
        return true;
    }

    @Transactional
    @PutMapping("/api/modUsuarioRol/{usu}/{rol}")
    public boolean eliminarUsuarioRol(@PathVariable String usu, @PathVariable Long rol) {
        usuariosRolesRepository.deleteUsuarioRol(usu, rol);
        return true;
    }

    @GetMapping("/api/rolOdontologos")
    public List<UsuariosRolesModel> obtenerOdontologos() {
        return usuariosRolesRepository.findByRolIdRol(2L);
    }

    @GetMapping("/api/rolPacientes")
    public List<UsuariosRolesModel> obtenerPacientes() {
        return usuariosRolesRepository.findByRolIdRol(3L);
    }
}
