package com.server.DentalCenterLlanos.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.DentalCenterLlanos.Model.RolesModel;
import com.server.DentalCenterLlanos.Repository.RolesRepository;



@CrossOrigin(origins = "*")
@RestController
public class RolesController {
	@Autowired
	private RolesRepository rolesRepository;

	@GetMapping("/api/listaRoles")
	public List<RolesModel> ListarRoles() {
		return rolesRepository.findAll();

	}
}