package com.server.DentalCenterLlanos.Controller;

import com.server.DentalCenterLlanos.Model.ModulosModel;
import com.server.DentalCenterLlanos.Repository.ModulosRepository;
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
@RequestMapping("/api/modulos")
public class ModulosController {

    @Autowired
    private ModulosRepository modulosRepository;

    public ModulosController(ModulosRepository modulosRepository) {
        this.modulosRepository = modulosRepository;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        Map<String, Object> response = new HashMap<>();
        List<ModulosModel> modulos = modulosRepository.findAll();
        response.put("modulos", modulos);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@RequestBody ModulosModel modulo) {
        Map<String, Object> response = new HashMap<>();
        ModulosModel savedModulo = modulosRepository.save(modulo);
        response.put("modulo", savedModulo);
        response.put("message", "Módulo creado exitosamente.");
        return ResponseEntity.ok(response);
    }
}