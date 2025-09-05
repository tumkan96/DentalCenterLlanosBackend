package com.server.DentalCenterLlanos.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.server.DentalCenterLlanos.Model.PersonasModel;
import com.server.DentalCenterLlanos.Repository.PersonasRepository;

@CrossOrigin(origins = "*")
@RestController
public class PersonasController {

	@Autowired
	private PersonasRepository personasRepository;

	@GetMapping("/api/listarPersonas")
	public ResponseEntity<List<PersonasModel>> listarPersonas() {
		List<PersonasModel> personas = personasRepository.findAll();
		if (personas.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(personas, HttpStatus.OK);
	}

	@PostMapping("/api/addPersona")
	public ResponseEntity<PersonasModel> adicionarPersona(@RequestBody PersonasModel nuevaPersona) {
		PersonasModel personaGuardada = personasRepository.save(nuevaPersona);
		return ResponseEntity.status(HttpStatus.CREATED).body(personaGuardada);
	}

	@PutMapping("/api/updatePersona/{idPersona}")
	public ResponseEntity<Object> updatePersona(@PathVariable("idPersona") Long idPersona,
			@RequestBody PersonasModel personaActualizada) {
		Optional<PersonasModel> optionalPersona = personasRepository.findById(idPersona);
		if (optionalPersona.isPresent()) {
			PersonasModel personaExistente = optionalPersona.get();

			personaExistente.setNombres(personaActualizada.getNombres());
			personaExistente.setApellidoPaterno(personaActualizada.getApellidoPaterno());
			personaExistente.setApellidoMaterno(personaActualizada.getApellidoMaterno());
			personaExistente.setCedulaIdentidad(personaActualizada.getCedulaIdentidad());
			personaExistente.setTelefonoCelular(personaActualizada.getTelefonoCelular());
			personaExistente.setFotografia(personaActualizada.getFotografia());

			personasRepository.save(personaExistente);

			Map<String, String> response = new HashMap<>();
			response.put("message", "Persona actualizada correctamente.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
		}
	}

	@PutMapping("/api/cambiarEstadoPersona/{idPersona}")
	public ResponseEntity<Object> cambiarEstadoPersona(@PathVariable("idPersona") Long idPersona,
			@RequestParam boolean estado) {
		Optional<PersonasModel> optionalPersona = personasRepository.findById(idPersona);
		if (optionalPersona.isPresent()) {
			PersonasModel persona = optionalPersona.get();
			persona.setEstado(estado);
			personasRepository.save(persona);

			Map<String, String> response = new HashMap<>();
			response.put("message", "Persona " + (estado ? "habilitada" : "inhabilitada") + " correctamente.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
		}
	}

	@GetMapping("/api/persona/{idPersona}")
	public ResponseEntity<Object> obtenerPersonaPorId(@PathVariable("idPersona") Long idPersona) {
		Optional<PersonasModel> optionalPersona = personasRepository.findById(idPersona);
		if (optionalPersona.isPresent()) {
			return new ResponseEntity<>(optionalPersona.get(), HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
		}
	}

	@DeleteMapping("/api/deletePersona/{idPersona}")
	public ResponseEntity<Object> eliminarPersona(@PathVariable("idPersona") Long idPersona) {
		Optional<PersonasModel> optionalPersona = personasRepository.findById(idPersona);
		if (optionalPersona.isPresent()) {
			personasRepository.deleteById(idPersona);

			Map<String, String> response = new HashMap<>();
			response.put("message", "Persona eliminada correctamente.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
		}
	}

}
