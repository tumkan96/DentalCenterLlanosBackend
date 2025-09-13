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

    private UsuarioRolDTO mapToDTO(UsuariosRolesModel model) {
        UsuarioRolDTO dto = new UsuarioRolDTO();

        RolDTO rolDTO = new RolDTO();
        rolDTO.setIdRol(model.getRol().getIdRol());
        rolDTO.setNombre(model.getRol().getNombre());
        rolDTO.setDescripcion(model.getRol().getDescripcion());
        rolDTO.setEstado(model.getRol().isEstado());
        rolDTO.setModulos(new ArrayList<>());

        dto.setRol(rolDTO);
        return dto;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioRolDTO>> listarUsuariosRoles() {
        List<UsuariosRolesModel> lista = usuariosRolesRepository.findAll();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<UsuarioRolDTO> dtoList = lista.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<List<UsuarioRolDTO>> obtenerRolesPorUsuario(@PathVariable String usuario) {
        List<UsuariosRolesModel> relaciones = usuariosRolesRepository.findByUsuario(usuario);
        if (relaciones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<UsuarioRolDTO> rolesDTO = relaciones.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(rolesDTO, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/asignar/{usuario}/{rolId}")
    public ResponseEntity<Object> asignarUsuarioRol(@PathVariable String usuario, @PathVariable Long rolId) {
        try {
            usuariosRolesRepository.addUsuarioRol(usuario, rolId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Rol asignado correctamente.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al asignar rol: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @DeleteMapping("/eliminar/{usuario}/{rolId}")
    public ResponseEntity<Object> eliminarUsuarioRol(@PathVariable String usuario, @PathVariable Long rolId) {
        try {
            usuariosRolesRepository.deleteUsuarioRol(usuario, rolId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Rol eliminado correctamente.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar rol: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/odontologos")
    public ResponseEntity<List<UsuarioRolDTO>> obtenerOdontologos() {
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
