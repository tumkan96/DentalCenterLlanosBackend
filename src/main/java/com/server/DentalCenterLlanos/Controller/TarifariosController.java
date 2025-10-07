package com.server.DentalCenterLlanos.Controller;

import com.server.DentalCenterLlanos.Dtos.TarifariosDto;
import com.server.DentalCenterLlanos.Dtos.TarifariosDetalleDto;
import com.server.DentalCenterLlanos.Dtos.TratamientosDto;
import com.server.DentalCenterLlanos.Model.TarifariosModel;
import com.server.DentalCenterLlanos.Model.TratamientosModel;
import com.server.DentalCenterLlanos.Repository.TarifariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RestController
public class TarifariosController {

    @Autowired
    private TarifariosRepository tarifariosRepository;

    @GetMapping("/tarifarios")
    public ResponseEntity<List<TarifariosDto>> getAllTarifarios() {
        List<TarifariosDto> tarifarios = tarifariosRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(tarifarios, HttpStatus.OK);
    }

    @GetMapping("/tarifarios/{id}")
    public ResponseEntity<TarifariosDto> getTarifarioById(@PathVariable Long id) {
        return tarifariosRepository.findById(id)
                .map(this::convertToDto)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/tarifarios")
    public ResponseEntity<TarifariosDto> createTarifario(@RequestBody TarifariosDto tarifariosDto) {
        TarifariosModel tarifario = convertToEntity(tarifariosDto);
        TarifariosModel savedTarifario = tarifariosRepository.save(tarifario);
        return new ResponseEntity<>(convertToDto(savedTarifario), HttpStatus.CREATED);
    }

    @PutMapping("/tarifarios/{id}")
    public ResponseEntity<TarifariosDto> updateTarifario(@PathVariable Long id, @RequestBody TarifariosDto tarifariosDto) {
        if (!tarifariosRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TarifariosModel tarifario = convertToEntity(tarifariosDto);
        tarifario.setIdTarifario(id);
        TarifariosModel updatedTarifario = tarifariosRepository.save(tarifario);
        return new ResponseEntity<>(convertToDto(updatedTarifario), HttpStatus.OK);
    }

    @DeleteMapping("/tarifarios/{id}")
    public ResponseEntity<Void> deleteTarifario(@PathVariable Long id) {
        if (!tarifariosRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tarifariosRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/tarifarios/cambiarEstado/{id}")
    public ResponseEntity<Object> cambiarEstadoTarifario(@PathVariable Long id, @RequestParam(required = false) Boolean estado) {
        Optional<TarifariosModel> optionalTarifario = tarifariosRepository.findById(id);
        if (optionalTarifario.isPresent()) {
            TarifariosModel tarifario = optionalTarifario.get();
            boolean nuevoEstado = (estado != null) ? estado : !tarifario.isEstado();
            tarifario.setEstado(nuevoEstado);
            tarifariosRepository.save(tarifario);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Tarifario " + (nuevoEstado ? "habilitado" : "inhabilitado") + " correctamente.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Tarifario no encontrado.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    private TarifariosDto convertToDto(TarifariosModel tarifario) {
        TarifariosDto dto = new TarifariosDto();
        dto.setIdTarifario(tarifario.getIdTarifario());
        dto.setNombre(tarifario.getNombre());
        dto.setFechaVigencia(tarifario.getFechaVigencia());
        dto.setEstado(tarifario.isEstado());
        // Mapear detalles como objetos completos
        List<TarifariosDetalleDto> detallesDtos = tarifario.getDetalles().stream()
                .map(td -> {
                    TarifariosDetalleDto tdDto = new TarifariosDetalleDto();
                    tdDto.setIdTarifarioDetalle(td.getIdTarifarioDetalle());
                    tdDto.setPrecio(td.getPrecio());
                    // Mapear Tratamiento
                    TratamientosModel tratamiento = td.getTratamiento();
                    TratamientosDto tratamientoDto = new TratamientosDto();
                    tratamientoDto.setIdTratamiento(tratamiento.getIdTratamiento());
                    tratamientoDto.setNombre(tratamiento.getNombre());
                    tratamientoDto.setDescripcion(tratamiento.getDescripcion());
                    tratamientoDto.setCodigoCupsCie10(tratamiento.getCodigoCupsCie10());
                    tratamientoDto.setEstado(tratamiento.isEstado());
                    tdDto.setTratamiento(tratamientoDto);
                    // Evitar ciclo infinito omitiendo tarifario en TarifariosDetalleDto
                    return tdDto;
                })
                .collect(Collectors.toList());
        dto.setDetalles(detallesDtos);
        return dto;
    }

    private TarifariosModel convertToEntity(TarifariosDto dto) {
        TarifariosModel tarifario = new TarifariosModel();
        tarifario.setNombre(dto.getNombre());
        tarifario.setFechaVigencia(dto.getFechaVigencia());
        tarifario.setEstado(dto.isEstado());
        return tarifario;
    }
}