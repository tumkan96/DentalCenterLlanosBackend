package com.server.DentalCenterLlanos.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.server.DentalCenterLlanos.Dtos.Personas.PersonaDTO;
import com.server.DentalCenterLlanos.Dtos.UsuariosDTO.UsuarioDTO;
import com.server.DentalCenterLlanos.Model.PersonasModel;
import com.server.DentalCenterLlanos.Repository.PersonasRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PersonasController {

	@Autowired
	private PersonasRepository personasRepository;

	private PersonaDTO mapToDTO(PersonasModel persona) {
		PersonaDTO dto = new PersonaDTO();
		dto.setIdPersona(persona.getIdPersona());
		dto.setCedulaIdentidad(persona.getCedulaIdentidad());
		dto.setNombres(persona.getNombres());
		dto.setApellidoPaterno(persona.getApellidoPaterno());
		dto.setApellidoMaterno(persona.getApellidoMaterno());
		dto.setFotografia(persona.getFotografia());
		dto.setTelefonoCelular(persona.getTelefonoCelular());
		dto.setEstado(persona.isEstado());

		 if (persona.getUsuario() != null) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsuarios(persona.getUsuario().getUsuarios());
        usuarioDTO.setEstado(persona.getUsuario().isEstado());

      

        dto.setUsuario(usuarioDTO);
    }
		return dto;
	}

	private PersonasModel mapToModel(PersonaDTO dto) {
		PersonasModel persona = new PersonasModel();
		persona.setIdPersona(dto.getIdPersona());
		persona.setCedulaIdentidad(dto.getCedulaIdentidad());
		persona.setNombres(dto.getNombres());
		persona.setApellidoPaterno(dto.getApellidoPaterno());
		persona.setApellidoMaterno(dto.getApellidoMaterno());
		persona.setFotografia(dto.getFotografia());
		persona.setTelefonoCelular(dto.getTelefonoCelular());
		persona.setEstado(dto.isEstado());
		return persona;
	}

	@GetMapping("/listarPersonas")
	public ResponseEntity<List<PersonaDTO>> listarPersonas() {
		List<PersonasModel> personas = personasRepository.findAll();
		if (personas.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<PersonaDTO> dtoList = personas.stream()
				.map(this::mapToDTO)
				.collect(Collectors.toList());
		return new ResponseEntity<>(dtoList, HttpStatus.OK);
	}

	@PostMapping("/addPersona")
	public ResponseEntity<PersonaDTO> adicionarPersona(@RequestBody PersonaDTO nuevaPersonaDTO) {
		PersonasModel persona = mapToModel(nuevaPersonaDTO);
		persona.setIdPersona(null);
		PersonasModel personaGuardada = personasRepository.save(persona);
		PersonaDTO responseDTO = mapToDTO(personaGuardada);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
	}

	@PutMapping("/updatePersona/{idPersona}")
	public ResponseEntity<Object> updatePersona(@PathVariable Long idPersona,
			@RequestBody PersonaDTO personaActualizadaDTO) {
		Optional<PersonasModel> optionalPersona = personasRepository.findById(idPersona);
		if (optionalPersona.isPresent()) {
			PersonasModel personaExistente = optionalPersona.get();

			personaExistente.setNombres(personaActualizadaDTO.getNombres());
			personaExistente.setApellidoPaterno(personaActualizadaDTO.getApellidoPaterno());
			personaExistente.setApellidoMaterno(personaActualizadaDTO.getApellidoMaterno());
			personaExistente.setCedulaIdentidad(personaActualizadaDTO.getCedulaIdentidad());
			personaExistente.setTelefonoCelular(personaActualizadaDTO.getTelefonoCelular());
			personaExistente.setFotografia(personaActualizadaDTO.getFotografia());
			personaExistente.setEstado(personaActualizadaDTO.isEstado());

			personasRepository.save(personaExistente);

			Map<String, String> response = new HashMap<>();
			response.put("message", "Persona actualizada correctamente.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
		}
	}

	@PutMapping("/cambiarEstadoPersona/{idPersona}")
	public ResponseEntity<Object> cambiarEstadoPersona(@PathVariable Long idPersona,
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

	@GetMapping("/persona/{idPersona}")
	public ResponseEntity<Object> obtenerPersonaPorId(@PathVariable Long idPersona) {
		Optional<PersonasModel> optionalPersona = personasRepository.findById(idPersona);
		if (optionalPersona.isPresent()) {
			PersonaDTO dto = mapToDTO(optionalPersona.get());
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
		}
	}

	@DeleteMapping("/deletePersona/{idPersona}")
	public ResponseEntity<Object> eliminarPersona(@PathVariable Long idPersona) {
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
