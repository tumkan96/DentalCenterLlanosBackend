package com.server.DentalCenterLlanos.Controller;

import com.server.DentalCenterLlanos.Model.RolesModel;
import com.server.DentalCenterLlanos.Repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class RolesController {

    @Autowired
    private RolesRepository rolesRepository;

    @GetMapping("/api/listarRoles")
    public ResponseEntity<List<RolesModel>> listarRoles() {
        List<RolesModel> roles = rolesRepository.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/api/verRol/{id}")
    public ResponseEntity<RolesModel> obtenerRolPorId(@PathVariable Long id) {
        Optional<RolesModel> rol = rolesRepository.findById(id);
        return rol.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                  .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/api/adicionarRol")
    public ResponseEntity<RolesModel> adicionarRol(@RequestBody RolesModel rol) {
        rol.setIdRol(null);
        RolesModel nuevoRol = rolesRepository.save(rol);
        return new ResponseEntity<>(nuevoRol, HttpStatus.CREATED);
    }

    @PutMapping("/api/modificarRol/{id}")
    public ResponseEntity<RolesModel> actualizarRol(@PathVariable Long id, @RequestBody RolesModel rolActualizado) {
        Optional<RolesModel> rolExistente = rolesRepository.findById(id);
        if (rolExistente.isPresent()) {
            RolesModel rol = rolExistente.get();
            rol.setNombre(rolActualizado.getNombre());
            rol.setDescripcion(rolActualizado.getDescripcion());
            RolesModel rolGuardado = rolesRepository.save(rol);
            return new ResponseEntity<>(rolGuardado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/api/cambiarEstadoRol/{id}")
    public ResponseEntity<Object> cambiarEstadoRol(@PathVariable Long id, @RequestParam boolean estado) {
        Optional<RolesModel> optionalRol = rolesRepository.findById(id);
        if (optionalRol.isPresent()) {
            RolesModel rol = optionalRol.get();
            rol.setEstado(estado);
            rolesRepository.save(rol);
            String mensaje = "Rol " + (estado ? "habilitado" : "inhabilitado") + " correctamente.";
            return new ResponseEntity<>(Map.of("message", mensaje), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado.");
    }

    @DeleteMapping("/api/eliminarRol/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        Optional<RolesModel> rol = rolesRepository.findById(id);
        if (rol.isPresent()) {
            rolesRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
