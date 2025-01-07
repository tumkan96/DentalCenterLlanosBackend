package com.server.DentalCenterLlanos.Controller;

import java.security.SecureRandom;
import java.util.ArrayList;
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

import com.server.DentalCenterLlanos.Repository.Usuarios_RolesRepository;
import com.server.DentalCenterLlanos.security.JwtUtil;
import com.server.DentalCenterLlanos.Repository.UsuariosRepository;
import com.server.DentalCenterLlanos.Repository.PersonasRepository;
import com.server.DentalCenterLlanos.Model.PersonasModel;
import com.server.DentalCenterLlanos.Model.UsuariosModel;
import com.server.DentalCenterLlanos.Model.Usuarios_RolesModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "*")

@RestController

public class UsuariosController {
	
	@Autowired
	private UsuariosRepository UsuariosRepository;
	@Autowired
	private PersonasRepository personasRepository;
	 @Autowired
	    private PasswordEncoder passwordEncoder; 
	 
	   public UsuariosController(UsuariosRepository usuariosRepository, 
               PersonasRepository personasRepository,
               PasswordEncoder passwordEncoder) {
this.UsuariosRepository = usuariosRepository;
this.personasRepository = personasRepository;
this.passwordEncoder = passwordEncoder;
}

	@GetMapping("/api/listarUsuarios")
	public List<UsuariosModel> listaUsuarios() {
		return UsuariosRepository.findAll();
	}

	// LOGIN
	/*@PostMapping("/api/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody UsuariosModel usuario) {
		Map<String, Object> response = new HashMap<>();

		try {
			// Buscar el usuario en la base de datos
			UsuariosModel user = UsuariosRepository.findByUsuarios(usuario.getUsuarios());

			// Validar si el usuario existe
			if (user != null) {
				// Verificar si el usuario está activo (cod_estado == 1)
				if (user.getCod_estado() == 0) {
					response.put("authenticated", false);
					response.put("message", "Usuario inactivo.");
					return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response); // 403 Forbidden
				}

				// Validar si la contraseña coincide
				if (user.getContrasena().equals(usuario.getContrasena())) {
					// Obtener roles del usuario desde la lista usuariosRoles
					List<Map<String, Object>> roles = new ArrayList<>();
					for (Usuarios_RolesModel usuarioRol : user.getUsuariosRoles()) {
						// Crear un mapa para cada rol con su id y nombre
						Map<String, Object> roleData = new HashMap<>();
						roleData.put("id_rol", usuarioRol.getRol().getId_rol());
						roleData.put("nombre", usuarioRol.getRol().getNombre());
						roles.add(roleData);
					}

					// Construir la respuesta
					response.put("authenticated", true);
					response.put("message", "Inicio de sesión exitoso");
					response.put("persona", user.getPersona());
					response.put("roles", roles);

					return ResponseEntity.ok(response);
				} else {
					// Respuesta en caso de que la contraseña no coincida
					response.put("authenticated", false);
					response.put("message", "Usuario o contraseña incorrectos.");
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // 401 Unauthorized
				}
			} else {
				// Respuesta en caso de que no se encuentre el usuario
				response.put("authenticated", false);
				response.put("message", "Usuario no encontrado.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // 404 Not Found
			}
		} catch (Exception e) {
			// Manejo de excepción
			response.put("authenticated", false);
			response.put("error", "Ocurrió un error durante la autenticación: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500 Internal Server Error
		}
	}
*/
	@PostMapping("/api/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody UsuariosModel usuario) {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        // Buscar el usuario en la base de datos
	        UsuariosModel user = UsuariosRepository.findByUsuarios(usuario.getUsuarios());

	        // Validar si el usuario existe
	        if (user != null) {
	            // Verificar si el usuario está activo
	            if (user.getCod_estado() == 0) {
	                response.put("authenticated", false);
	                response.put("message", "Usuario inactivo.");
	                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	            }

	            // Comparar la contraseña directamente (sin BCrypt)
	            if (usuario.getContrasena().equals(user.getContrasena())) {
	                // Generar el JWT
	                String token = "Bearer "+JwtUtil.generateToken(user.getUsuarios(), user.getUsuariosRoles());

	                // Obtener roles del usuario
	                List<Map<String, Object>> roles = new ArrayList<>();
	                for (Usuarios_RolesModel usuarioRol : user.getUsuariosRoles()) {
	                    Map<String, Object> roleData = new HashMap<>();
	                    roleData.put("id_rol", usuarioRol.getRol().getId_rol());
	                    roleData.put("nombre", usuarioRol.getRol().getNombre());
	                    roles.add(roleData);
	                }

	                // Construir la respuesta
	                response.put("authenticated", true);
	                response.put("message", "Inicio de sesión exitoso");
	                response.put("token", token);
	                response.put("persona", user.getPersona());
	                response.put("roles", roles);

	              //  System.out.println("Generated Token: " + token);

	                return ResponseEntity.ok(response);
	            } else {
	                response.put("authenticated", false);
	                response.put("message", "Usuario o contraseña incorrectos.");
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	            }
	        } else {
	            response.put("authenticated", false);
	            response.put("message", "Usuario no encontrado.");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    } catch (Exception e) {
	        response.put("authenticated", false);
	        response.put("error", "Ocurrió un error durante la autenticación: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	// HABILITAR USUARIO
	@PutMapping("/api/habilitarUsuario/{usuarios}")
	public ResponseEntity<Map<String, Object>> habilitarUsuario(@PathVariable String usuarios) {
		Map<String, Object> response = new HashMap<>();

		try {
			// Buscar el usuario en la base de datos
			UsuariosModel user = UsuariosRepository.findByUsuarios(usuarios);

			if (user != null) {
				// Verificar si el usuario ya está habilitado
				if (user.getCod_estado() == 1) {
					response.put("message", "El usuario ya está habilitado.");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}

				// Cambiar el estado del usuario a habilitado (cod_estado = 1)
				user.setCod_estado(1);
				UsuariosRepository.save(user);

				response.put("message", "Usuario habilitado exitosamente.");
				return ResponseEntity.ok(response);
			} else {
				response.put("message", "Usuario no encontrado.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			response.put("error", "Error al habilitar el usuario: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// DESHABILITAR USUARIO
	@PutMapping("/api/deshabilitarUsuario/{usuarios}")
	public ResponseEntity<Map<String, Object>> deshabilitarUsuario(@PathVariable String usuarios) {
		Map<String, Object> response = new HashMap<>();

		try {
			// Buscar el usuario en la base de datos
			UsuariosModel user = UsuariosRepository.findByUsuarios(usuarios);

			if (user != null) {
				// Verificar si el usuario ya está deshabilitado
				if (user.getCod_estado() == 0) {
					response.put("message", "El usuario ya está deshabilitado.");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}

				// Cambiar el estado del usuario a deshabilitado (cod_estado = 0)
				user.setCod_estado(0);
				UsuariosRepository.save(user);

				response.put("message", "Usuario deshabilitado exitosamente.");
				return ResponseEntity.ok(response);
			} else {
				response.put("message", "Usuario no encontrado.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			response.put("error", "Error al deshabilitar el usuario: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/api/obtenerUltimoUsuario")
	public ResponseEntity<Map<String, String>> obtenerUltimoUsuario() {
		Map<String, String> response = new HashMap<>();
		try {
			String ultimoUsuario = UsuariosRepository.findTopByOrderByUsuariosDesc().map(UsuariosModel::getUsuarios)
					.orElse("u0000");
			response.put("ultimoUsuario", ultimoUsuario);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("error", "Error al obtener el último usuario");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// Método para asignar usuario y contraseña a la persona
	@PostMapping("/api/asignarUserPassword/{seq_persona}")
	public ResponseEntity<Map<String, String>> asignarUserPassword(@PathVariable long seq_persona) {
		Map<String, String> response = new HashMap<>();

		// Obtener la persona a la que se asignará el usuario
		Optional<PersonasModel> personaOptional = personasRepository.findById(seq_persona);

		if (personaOptional.isPresent()) {
			PersonasModel persona = personaOptional.get();

			// Obtener el último usuario generado
			String ultimoUsuario = UsuariosRepository.findTopByOrderByUsuariosDesc().map(UsuariosModel::getUsuarios)
					.orElse("u0000");

			// Generar un nuevo usuario y una contraseña aleatoria
			String nuevoUsuario = generarNuevoUsuario(ultimoUsuario);
			String nuevaContrasena = generarContrasenaAleatoria();

			// Crear el nuevo usuario
			UsuariosModel nuevoUsuarioModel = new UsuariosModel();
			nuevoUsuarioModel.setUsuarios(nuevoUsuario);
			nuevoUsuarioModel.setContrasena(nuevaContrasena);
			nuevoUsuarioModel.setCod_estado(1);
			nuevoUsuarioModel.setPersona(persona);

			// Guardar el usuario en la base de datos
			UsuariosRepository.save(nuevoUsuarioModel);

			// Preparar la respuesta
			response.put("usuario", nuevoUsuario);
			response.put("contrasena", nuevaContrasena);
			response.put("message", "Usuario y contraseña generados exitosamente.");
			return ResponseEntity.ok(response);

		} else {
			// Persona no encontrada
			response.put("error", "No se encontró la persona con el ID proporcionado.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	// Método para generar el siguiente nombre de usuario
	private String generarNuevoUsuario(String ultimoUsuario) {
		int numero = Integer.parseInt(ultimoUsuario.substring(1)) + 1;
		return String.format("u%04d", numero);
	}

	// Método para generar una contraseña aleatoria
	private String generarContrasenaAleatoria() {
		String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		String simbolos = "/*-+";
		String numeros = "0123456789";
		SecureRandom random = new SecureRandom();

		// Generar letras aleatorias
		StringBuilder contrasena = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			contrasena.append(letras.charAt(random.nextInt(letras.length())));
		}

		// Agregar un símbolo aleatorio
		contrasena.append(simbolos.charAt(random.nextInt(simbolos.length())));

		// Agregar tres dígitos numéricos aleatorios
		for (int i = 0; i < 3; i++) {
			contrasena.append(numeros.charAt(random.nextInt(numeros.length())));
		}

		return contrasena.toString();
	}

}
