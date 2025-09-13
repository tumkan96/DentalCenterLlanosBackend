package com.server.DentalCenterLlanos.Controller;

import com.server.DentalCenterLlanos.Dtos.Roles.RolDTO;
import com.server.DentalCenterLlanos.Model.RolesModel;
import com.server.DentalCenterLlanos.Repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class RolesController {

    @Autowired
    private RolesRepository rolesRepository;

    private RolDTO mapToDTO(RolesModel rol) {
        RolDTO dto = new RolDTO();
        dto.setIdRol(rol.getIdRol());
        dto.setNombre(rol.getNombre());
        dto.setDescripcion(rol.getDescripcion());
        dto.setEstado(rol.isEstado());
        dto.setModulos(new ArrayList<>());
        return dto;
    }

    private RolesModel mapToModel(RolDTO dto) {
        RolesModel rol = new RolesModel();
        rol.setIdRol(dto.getIdRol());
        rol.setNombre(dto.getNombre());
        rol.setDescripcion(dto.getDescripcion());
        rol.setEstado(dto.isEstado());
        return rol;
    }

    @GetMapping("/listarRoles")
    public ResponseEntity<List<RolDTO>> listarRoles() {
        List<RolesModel> roles = rolesRepository.findAll();
        if (roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<RolDTO> dtoList = roles.stream().map(this::mapToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/verRol/{id}")
    public ResponseEntity<Object> obtenerRolPorId(@PathVariable Long id) {
        Optional<RolesModel> optionalRol = rolesRepository.findById(id);
        if (optionalRol.isPresent()) {
            RolDTO dto = mapToDTO(optionalRol.get());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "Rol no encontrado"), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/adicionarRol")
    public ResponseEntity<RolDTO> adicionarRol(@RequestBody RolDTO rolDTO) {
        RolesModel nuevoRol = mapToModel(rolDTO);
        nuevoRol.setIdRol(null);
        RolesModel guardado = rolesRepository.save(nuevoRol);
        RolDTO responseDTO = mapToDTO(guardado);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/modificarRol/{id}")
    public ResponseEntity<Object> actualizarRol(@PathVariable Long id, @RequestBody RolDTO rolDTO) {
        Optional<RolesModel> optionalRol = rolesRepository.findById(id);
        if (optionalRol.isPresent()) {
            RolesModel rol = optionalRol.get();
            rol.setNombre(rolDTO.getNombre());
            rol.setDescripcion(rolDTO.getDescripcion());
            rol.setEstado(rolDTO.isEstado());
            RolesModel actualizado = rolesRepository.save(rol);
            RolDTO responseDTO = mapToDTO(actualizado);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "Rol no encontrado"), HttpStatus.NOT_FOUND);
    }

    @PutMapping("/cambiarEstadoRol/{id}")
    public ResponseEntity<Map<String, String>> cambiarEstadoRol(@PathVariable Long id, @RequestParam boolean estado) {
        Optional<RolesModel> optionalRol = rolesRepository.findById(id);
        if (optionalRol.isPresent()) {
            RolesModel rol = optionalRol.get();
            rol.setEstado(estado);
            rolesRepository.save(rol);
            String mensaje = "Rol " + (estado ? "habilitado" : "inhabilitado") + " correctamente.";
            return new ResponseEntity<>(Map.of("message", mensaje), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "Rol no encontrado"), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/eliminarRol/{id}")
    public ResponseEntity<Map<String, String>> eliminarRol(@PathVariable Long id) {
        Optional<RolesModel> optionalRol = rolesRepository.findById(id);
        if (optionalRol.isPresent()) {
            rolesRepository.deleteById(id);
            return new ResponseEntity<>(Map.of("message", "Rol eliminado correctamente."), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "Rol no encontrado"), HttpStatus.NOT_FOUND);
    }
}
