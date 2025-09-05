package com.server.DentalCenterLlanos.Controller;

import com.server.DentalCenterLlanos.Model.PermisosModel;
import com.server.DentalCenterLlanos.Repository.PermisosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/permisos")
public class PermisosController {

    @Autowired
    private PermisosRepository permisosRepository;

    public PermisosController(PermisosRepository permisosRepository) {
        this.permisosRepository = permisosRepository;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        Map<String, Object> response = new HashMap<>();
        List<PermisosModel> permisos = permisosRepository.findAll();
        response.put("permisos", permisos);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@RequestBody PermisosModel permiso) {
        Map<String, Object> response = new HashMap<>();
        PermisosModel savedPermiso = permisosRepository.save(permiso);
        response.put("permiso", savedPermiso);
        response.put("message", "Permiso creado exitosamente.");
        return ResponseEntity.ok(response);
    }
}