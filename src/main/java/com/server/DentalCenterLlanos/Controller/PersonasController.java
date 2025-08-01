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
	public void adicionarPersonas(@RequestBody PersonasModel newPersona) {
		PersonasRepository.save(newPersona);
	}
	
	// INHABILITAR PERSONA
		@PutMapping("/api/inhabilPersona/{seq_persona}")
		public ResponseEntity<Object> inhabilPersona(@PathVariable("seq_persona") Long seq_persona) {
			Optional<PersonasModel> optionalPersona = PersonasRepository.findById(seq_persona);
			if (optionalPersona.isPresent()) {
				PersonasModel persona = optionalPersona.get();
				persona.setCod_estado(0);
				PersonasRepository.save(persona);

				// Crear un objeto JSON con un mensaje informativo
				Map<String, String> response = new HashMap<>();
				response.put("message", "Inhabilitado.");

				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				return ResponseEntity.notFound().build();
			}
		}

		// HABILITAR PERSONA
		@PutMapping("/api/habilPersona/{seq_persona}")
		public ResponseEntity<Object> habilPersona(@PathVariable("seq_persona") Long seq_persona) {
			Optional<PersonasModel> optionalPersona = PersonasRepository.findById(seq_persona);
			if (optionalPersona.isPresent()) {
				PersonasModel persona = optionalPersona.get();
				persona.setCod_estado(1);
				PersonasRepository.save(persona);

				Map<String, String> response = new HashMap<>();
				response.put("message", "Habilitado.");

				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				return ResponseEntity.notFound().build();
			}

		}
}
