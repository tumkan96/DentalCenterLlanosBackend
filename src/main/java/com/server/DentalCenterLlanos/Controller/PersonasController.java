package com.server.DentalCenterLlanos.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.DentalCenterLlanos.Model.PersonasModel;
import com.server.DentalCenterLlanos.Repository.PersonasRepository;

@CrossOrigin(origins = "*")
@RestController
public class PersonasController {
	@Autowired
	public PersonasRepository PersonasRepository;

	// LISTAR PERSONAS
	@Autowired
	@GetMapping("/api/ListarPersonas")
	public ResponseEntity<List<PersonasModel>> listarPersonas() {
		List<PersonasModel> personas = PersonasRepository.findAll();
		if (personas.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(personas, HttpStatus.OK);
	}

	// ADICIONAR PERSONA
	@PostMapping("/api/addPersona")
	public ResponseEntity<PersonasModel> adicionarPersonas(@RequestBody PersonasModel newPersona) {
		PersonasModel savedPersona = PersonasRepository.save(newPersona);
		return ResponseEntity.status(HttpStatus.CREATED) // 201
				.body(savedPersona); // opcional: puedes retornar null si no quieres retornar el objeto
	}

	// MODIFICAR PERSONA
	@PutMapping("/api/updatePersona/{id_persona}")
	public ResponseEntity<Object> updatePersona(@PathVariable("id_persona") Long id_persona,
			@RequestBody PersonasModel personaActualizada) {
		Optional<PersonasModel> optionalPersona = PersonasRepository.findById(id_persona);
		if (optionalPersona.isPresent()) {
			PersonasModel personaExistente = optionalPersona.get();

			// Actualizar campos permitidos
			personaExistente.setNombres(personaActualizada.getNombres());
			personaExistente.setApellido_paterno(personaActualizada.getApellido_paterno());
			personaExistente.setApellido_materno(personaActualizada.getApellido_materno());
			personaExistente.setCedula_identidad(personaActualizada.getCedula_identidad());
			personaExistente.setTelefono_celular(personaActualizada.getTelefono_celular());
			personaExistente.setFotografia(personaActualizada.getFotografia());

			PersonasRepository.save(personaExistente);

			Map<String, String> response = new HashMap<>();
			response.put("message", "Persona actualizada correctamente.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
		}
	}

	// INHABILITAR PERSONA
	@PutMapping("/api/inhabilPersona/{id_persona}")
	public ResponseEntity<Object> inhabilPersona(@PathVariable("id_persona") Long id_persona) {
		Optional<PersonasModel> optionalPersona = PersonasRepository.findById(id_persona);
		if (optionalPersona.isPresent()) {
			PersonasModel persona = optionalPersona.get();
			persona.setCod_estado(0);
			PersonasRepository.save(persona);

			Map<String, String> response = new HashMap<>();
			response.put("message", "Persona inhabilitada correctamente.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
		}
	}

	// HABILITAR PERSONA
	@PutMapping("/api/habilPersona/{id_persona}")
	public ResponseEntity<Object> habilPersona(@PathVariable("id_persona") Long id_persona) {
		Optional<PersonasModel> optionalPersona = PersonasRepository.findById(id_persona);
		if (optionalPersona.isPresent()) {
			PersonasModel persona = optionalPersona.get();
			persona.setCod_estado(1);
			PersonasRepository.save(persona);

			Map<String, String> response = new HashMap<>();
			response.put("message", "Persona habilitada correctamente.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
		}
	}

}
