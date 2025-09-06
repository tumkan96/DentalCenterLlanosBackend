package com.server.DentalCenterLlanos.Controller;

import java.security.SecureRandom;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.DentalCenterLlanos.security.JwtUtil;
import com.server.DentalCenterLlanos.Repository.UsuariosRepository;
import com.server.DentalCenterLlanos.Repository.PersonasRepository;
import com.server.DentalCenterLlanos.Dtos.Auth.LoginResponseDTO;
import com.server.DentalCenterLlanos.Dtos.ModulosDTO.ModuloDTO;
import com.server.DentalCenterLlanos.Dtos.PermisosDTO.PermisoDTO;
import com.server.DentalCenterLlanos.Dtos.Personas.PersonaDTO;
import com.server.DentalCenterLlanos.Dtos.Roles.RolDTO;
import com.server.DentalCenterLlanos.Model.PersonasModel;
import com.server.DentalCenterLlanos.Model.UsuariosModel;


@CrossOrigin(origins = "*")
@RestController
public class UsuariosController {

    @Autowired
    private UsuariosRepository UsuariosRepository;

    @Autowired
    private PersonasRepository personasRepository;

    public UsuariosController(UsuariosRepository usuariosRepository,
            PersonasRepository personasRepository) {
        this.UsuariosRepository = usuariosRepository;
        this.personasRepository = personasRepository;
    }

    @GetMapping("/api/listarUsuarios")
    public List<UsuariosModel> listaUsuarios() {
        return UsuariosRepository.findAll();
    }

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UsuariosModel usuario) {
        LoginResponseDTO response = new LoginResponseDTO();

        try {
            UsuariosModel user = UsuariosRepository.findByUsuarios(usuario.getUsuarios());

            if (user == null) {
                response.setAuthenticated(false);
                response.setMessage("Usuario no encontrado.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            if (!user.isEstado()) {
                response.setAuthenticated(false);
                response.setMessage("Usuario inactivo.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }

            if (!usuario.getContrasena().equals(user.getContrasena())) {
                response.setAuthenticated(false);
                response.setMessage("Usuario o contraseña incorrectos.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            // ✅ Token
            String token = "Bearer " + JwtUtil.generateToken(user.getUsuarios(), user.getUsuariosRoles());

            // ✅ Convertir persona
            PersonaDTO personaDTO = new PersonaDTO();
            personaDTO.setIdPersona(user.getPersona().getIdPersona());
            personaDTO.setNombres(user.getPersona().getNombres());
            personaDTO.setApellidoPaterno(user.getPersona().getApellidoPaterno());
            personaDTO.setApellidoMaterno(user.getPersona().getApellidoMaterno());
            // ... mapea los demás campos de persona que necesites

            List<RolDTO> roles = user.getUsuariosRoles().stream().map(ur -> {
                RolDTO rolDTO = new RolDTO();
                rolDTO.setIdRol(ur.getRol().getIdRol());
                rolDTO.setNombre(ur.getRol().getNombre());

                // Módulos
                rolDTO.setModulos(
                        ur.getRol().getRolesModulos().stream().map(rm -> {
                            ModuloDTO moduloDTO = new ModuloDTO();
                            moduloDTO.setIdModulo(rm.getModulo().getIdModulo());
                            moduloDTO.setNombre(rm.getModulo().getNombre());
                            moduloDTO.setEstado(rm.getModulo().isEstado());

                            // Permisos del módulo
                            moduloDTO.setPermisos(
                                    rm.getModulo().getModulosPermisos().stream().map(mp -> {
                                        PermisoDTO permisoDTO = new PermisoDTO();
                                        permisoDTO.setIdPermiso(mp.getPermiso().getIdPermiso());
                                        permisoDTO.setAccion(mp.getPermiso().getAccion());
                                        permisoDTO.setEstado(mp.getPermiso().isEstado());
                                        return permisoDTO;
                                    }).toList());

                            return moduloDTO;
                        }).toList());

                return rolDTO;
            }).toList();

            // ✅ Construir respuesta
            response.setAuthenticated(true);
            response.setMessage("Inicio de sesión exitoso");
            response.setToken(token);
            response.setPersona(personaDTO);
            response.setRoles(roles);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setAuthenticated(false);
            response.setMessage("Ocurrió un error durante la autenticación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/api/cambiarEstadoUsuario/{usuarios}")
    public ResponseEntity<Object> cambiarEstadoUsuario(@PathVariable("usuarios") String usuarios,
            @RequestParam boolean estado) {
        Optional<UsuariosModel> optionalUsuario = UsuariosRepository.findById(usuarios);
        if (optionalUsuario.isPresent()) {
            UsuariosModel usuario = optionalUsuario.get();
            usuario.setEstado(estado);
            UsuariosRepository.save(usuario);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuario " + (estado ? "habilitado" : "deshabilitado") + " correctamente.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
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

    @PostMapping("/api/asignarUserPassword/{id_persona}")
    public ResponseEntity<Map<String, String>> asignarUserPassword(@PathVariable long id_persona) {
        Map<String, String> response = new HashMap<>();
        Optional<PersonasModel> personaOptional = personasRepository.findById(id_persona);
        if (personaOptional.isPresent()) {
            PersonasModel persona = personaOptional.get();
            String ultimoUsuario = UsuariosRepository.findTopByOrderByUsuariosDesc().map(UsuariosModel::getUsuarios)
                    .orElse("u0000");
            String nuevoUsuario = generarNuevoUsuario(ultimoUsuario);
            String nuevaContrasena = generarContrasenaAleatoria();
            UsuariosModel nuevoUsuarioModel = new UsuariosModel();
            nuevoUsuarioModel.setUsuarios(nuevoUsuario);
            nuevoUsuarioModel.setContrasena(nuevaContrasena);
            nuevoUsuarioModel.setEstado(true);
            nuevoUsuarioModel.setPersona(persona);
            UsuariosRepository.save(nuevoUsuarioModel);
            response.put("usuario", nuevoUsuario);
            response.put("contrasena", nuevaContrasena);
            response.put("message", "Usuario y contraseña generados exitosamente.");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "No se encontró la persona con el ID proporcionado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/api/autogenerarContrasena/{usuarios}")
    public ResponseEntity<Map<String, Object>> autogenerarContrasena(@PathVariable String usuarios) {
        Map<String, Object> response = new HashMap<>();
        try {
            UsuariosModel user = UsuariosRepository.findByUsuarios(usuarios);
            if (user != null) {
                String nuevaContrasena = generarContrasenaAleatoria();
                user.setContrasena(nuevaContrasena);
                UsuariosRepository.save(user);
                response.put("contrasena", nuevaContrasena);
                response.put("message", "Contraseña actualizada exitosamente.");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Usuario no encontrado.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("error", "Error al actualizar la contraseña: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private String generarNuevoUsuario(String ultimoUsuario) {
        int numero = Integer.parseInt(ultimoUsuario.substring(1)) + 1;
        return String.format("u%04d", numero);
    }

    private String generarContrasenaAleatoria() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String simbolos = "/*-+";
        String numeros = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder contrasena = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            contrasena.append(letras.charAt(random.nextInt(letras.length())));
        }
        contrasena.append(simbolos.charAt(random.nextInt(simbolos.length())));
        for (int i = 0; i < 3; i++) {
            contrasena.append(numeros.charAt(random.nextInt(numeros.length())));
        }
        return contrasena.toString();
    }
}
