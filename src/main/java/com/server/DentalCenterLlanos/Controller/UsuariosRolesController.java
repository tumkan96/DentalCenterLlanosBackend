package com.server.DentalCenterLlanos.Controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.server.DentalCenterLlanos.Dtos.Roles.RolDTO;
import com.server.DentalCenterLlanos.Dtos.UsuarioRolDTO.UsuarioRolDTO;
import com.server.DentalCenterLlanos.Model.UsuariosRolesModel;
import com.server.DentalCenterLlanos.Repository.UsuariosRolesRepository;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuariosRoles")
public class UsuariosRolesController {

    @Autowired
    private UsuariosRolesRepository usuariosRolesRepository;

    public UsuariosRolesController() {
        System.out.println("UsuariosRolesController inicializado correctamente");
    }

    private UsuarioRolDTO mapToDTO(UsuariosRolesModel model) {
        UsuarioRolDTO dto = new UsuarioRolDTO();

        // Evita errores por null
        if (model.getUsuario() != null) {
            dto.setUsuario(model.getUsuario().getUsuarios()); // Asume que getUsuarios() devuelve el ID tipo String
        }

        if (model.getRol() != null) {
            RolDTO rolDTO = new RolDTO();
            rolDTO.setIdRol(model.getRol().getIdRol());
            rolDTO.setNombre(model.getRol().getNombre());
            rolDTO.setDescripcion(model.getRol().getDescripcion());
            rolDTO.setEstado(model.getRol().isEstado());
            rolDTO.setModulos(new ArrayList<>());

            dto.setRol(rolDTO);
        }

        return dto;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioRolDTO>> listarUsuariosRoles() {
        System.out.println("Llamada a listarUsuariosRoles");
        List<UsuariosRolesModel> lista = usuariosRolesRepository.findAll();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<UsuarioRolDTO> dtoList = lista.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/verUsuarioRoles/{usuario}")
    public ResponseEntity<List<UsuarioRolDTO>> verUsuarioRoles(@PathVariable String usuario) {
        System.out.println("Llamada a verUsuarioRoles para usuario: " + usuario);
        List<UsuariosRolesModel> relaciones = usuariosRolesRepository.findByUsuarioUsuarios(usuario);
        if (relaciones == null || relaciones.isEmpty()) {
            System.out.println("No se encontraron roles para usuario: " + usuario);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<UsuarioRolDTO> rolesDTO = relaciones.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        System.out.println("Roles encontrados para usuario " + usuario + ": " + rolesDTO.size());
        return new ResponseEntity<>(rolesDTO, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/asignar/{usuario}/{rolId}")
    public ResponseEntity<Object> asignarUsuarioRol(@PathVariable String usuario, @PathVariable Long rolId) {
        System.out.println("Llamada a asignarUsuarioRol para usuario: " + usuario + ", rol: " + rolId);
        try {
            usuariosRolesRepository.addUsuarioRol(usuario, rolId);
            System.out.println("Rol " + rolId + " asignado a usuario " + usuario);

            return new ResponseEntity<>(Map.of("message", "Rol asignado correctamente."), HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error al asignar rol para usuario " + usuario + ": " + e.getMessage());

            return new ResponseEntity<>(Map.of("error", "Error al asignar rol: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @DeleteMapping("/eliminar/{usuario}/{rolId}")
    public ResponseEntity<Object> eliminarUsuarioRol(@PathVariable String usuario, @PathVariable Long rolId) {
        System.out.println("Llamada a eliminarUsuarioRol para usuario: " + usuario + ", rol: " + rolId);
        try {
            usuariosRolesRepository.deleteUsuarioRol(usuario, rolId);
            System.out.println("Rol " + rolId + " eliminado de usuario " + usuario);

            return new ResponseEntity<>(Map.of("message", "Rol eliminado correctamente."), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error al eliminar rol para usuario " + usuario + ": " + e.getMessage());

            return new ResponseEntity<>(Map.of("error", "Error al eliminar rol: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/odontologos")
    public ResponseEntity<List<UsuarioRolDTO>> obtenerOdontologos() {
        System.out.println("Llamada a obtenerOdontologos");
        List<UsuariosRolesModel> lista = usuariosRolesRepository.findByRolIdRol(2L);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<UsuarioRolDTO> dtoList = lista.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/pacientes")
    public ResponseEntity<List<UsuarioRolDTO>> obtenerPacientes() {
        System.out.println("Llamada a obtenerPacientes");
        List<UsuariosRolesModel> lista = usuariosRolesRepository.findByRolIdRol(3L);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<UsuarioRolDTO> dtoList = lista.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
