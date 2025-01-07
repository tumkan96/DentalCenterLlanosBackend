package com.server.DentalCenterLlanos.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.DentalCenterLlanos.Model.Usuarios_RolesModel;
import com.server.DentalCenterLlanos.Repository.Usuarios_RolesRepository;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = "*")
@RestController

public class Usuarios_RolesController {

	@Autowired
	public Usuarios_RolesRepository Usuarios_RolesRepository;

	@GetMapping("/api/listausuarioRoles")
	public List<Usuarios_RolesModel> ListaUsuarioRol() {
		return Usuarios_RolesRepository.findAll();
	}

	// ADICIONAMOS UN ROL A UN USUARIO: USER/1
	@Transactional
	@PostMapping("/api/rolme/addUsuario_Rol/{usu}/{rol}")
	public boolean mod_Rol4(@PathVariable String usu, @PathVariable int rol) {
		Usuarios_RolesRepository.add_Usuario_rol(usu, rol);
		return true;
	}

	// MODIFICAMOS UN ROL A UN USUARIO: USER/1
	@Transactional
	@PutMapping("/api/rolme/modUsuario_Rol/{usu}/{rol}")
	public boolean mod_Rol3(@PathVariable String usu, @PathVariable int rol) {
		Usuarios_RolesRepository.del_Usuario_rol2(usu, rol);
		return true;
	}

	@GetMapping("/api/rolOdontologos")
	public List<Usuarios_RolesModel> obtenerOdontologos() {
		return Usuarios_RolesRepository.findByRolIdRol(2); // id_rol = 2
	}

	@GetMapping("/api/rolPacientes")
	public List<Usuarios_RolesModel> obtenerPacientes() {
		return Usuarios_RolesRepository.findByRolIdRol(3); // id_rol = 2
	}

}
