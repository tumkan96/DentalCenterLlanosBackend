package com.server.DentalCenterLlanos.Controller;

import com.server.DentalCenterLlanos.Dtos.TarifariosDetalleDto;
import com.server.DentalCenterLlanos.Dtos.TarifariosDto;
import com.server.DentalCenterLlanos.Dtos.TratamientosDto;
import com.server.DentalCenterLlanos.Model.TarifariosDetalleModel;
import com.server.DentalCenterLlanos.Model.TarifariosModel;
import com.server.DentalCenterLlanos.Model.TratamientosModel;
import com.server.DentalCenterLlanos.Repository.TarifariosDetalleRepository;
import com.server.DentalCenterLlanos.Repository.TarifariosRepository;
import com.server.DentalCenterLlanos.Repository.TratamientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RestController
public class TarifariosDetalleController {

    @Autowired
    private TarifariosDetalleRepository tarifariosDetalleRepository;

    @Autowired
    private TarifariosRepository tarifariosRepository;

    @Autowired
    private TratamientosRepository tratamientosRepository;

    @GetMapping("/tarifarios-detalle")
    public ResponseEntity<List<TarifariosDetalleDto>> getAllTarifariosDetalle() {
        List<TarifariosDetalleDto> detalles = tarifariosDetalleRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(detalles, HttpStatus.OK);
    }

    @GetMapping("/tarifarios-detalle/{id}")
    public ResponseEntity<TarifariosDetalleDto> getTarifarioDetalleById(@PathVariable Long id) {
        return tarifariosDetalleRepository.findById(id)
                .map(this::convertToDto)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/tarifarios-detalle")
    public ResponseEntity<TarifariosDetalleDto> createTarifarioDetalle(@RequestBody TarifariosDetalleDto tarifariosDetalleDto) {
        TarifariosModel tarifario = tarifariosRepository.findById(tarifariosDetalleDto.getTarifario().getIdTarifario())
                .orElseThrow(() -> new RuntimeException("Tarifario not found"));
        TratamientosModel tratamiento = tratamientosRepository.findById(tarifariosDetalleDto.getTratamiento().getIdTratamiento())
                .orElseThrow(() -> new RuntimeException("Tratamiento not found"));
        TarifariosDetalleModel detalle = convertToEntity(tarifariosDetalleDto);
        detalle.setTarifario(tarifario);
        detalle.setTratamiento(tratamiento);
        TarifariosDetalleModel savedDetalle = tarifariosDetalleRepository.save(detalle);
        return new ResponseEntity<>(convertToDto(savedDetalle), HttpStatus.CREATED);
    }

    @PutMapping("/tarifarios-detalle/{id}")
    public ResponseEntity<TarifariosDetalleDto> updateTarifarioDetalle(@PathVariable Long id, @RequestBody TarifariosDetalleDto tarifariosDetalleDto) {
        if (!tarifariosDetalleRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TarifariosModel tarifario = tarifariosRepository.findById(tarifariosDetalleDto.getTarifario().getIdTarifario())
                .orElseThrow(() -> new RuntimeException("Tarifario not found"));
        TratamientosModel tratamiento = tratamientosRepository.findById(tarifariosDetalleDto.getTratamiento().getIdTratamiento())
                .orElseThrow(() -> new RuntimeException("Tratamiento not found"));
        TarifariosDetalleModel detalle = convertToEntity(tarifariosDetalleDto);
        detalle.setIdTarifarioDetalle(id);
        detalle.setTarifario(tarifario);
        detalle.setTratamiento(tratamiento);
        TarifariosDetalleModel updatedDetalle = tarifariosDetalleRepository.save(detalle);
        return new ResponseEntity<>(convertToDto(updatedDetalle), HttpStatus.OK);
    }

    @DeleteMapping("/tarifarios-detalle/{id}")
    public ResponseEntity<Void> deleteTarifarioDetalle(@PathVariable Long id) {
        if (!tarifariosDetalleRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tarifariosDetalleRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private TarifariosDetalleDto convertToDto(TarifariosDetalleModel detalle) {
        TarifariosDetalleDto dto = new TarifariosDetalleDto();
        dto.setIdTarifarioDetalle(detalle.getIdTarifarioDetalle());

        // Mapear TarifariosDto
        TarifariosModel tarifario = detalle.getTarifario();
        TarifariosDto tarifarioDto = new TarifariosDto();
        tarifarioDto.setIdTarifario(tarifario.getIdTarifario());
        tarifarioDto.setNombre(tarifario.getNombre());
        tarifarioDto.setFechaVigencia(tarifario.getFechaVigencia());
        tarifarioDto.setEstado(tarifario.isEstado());
        dto.setTarifario(tarifarioDto);

        // Mapear TratamientosDto
        TratamientosModel tratamiento = detalle.getTratamiento();
        TratamientosDto tratamientoDto = new TratamientosDto();
        tratamientoDto.setIdTratamiento(tratamiento.getIdTratamiento());
        tratamientoDto.setNombre(tratamiento.getNombre());
        tratamientoDto.setDescripcion(tratamiento.getDescripcion());
        tratamientoDto.setCodigoCupsCie10(tratamiento.getCodigoCupsCie10());
        tratamientoDto.setEstado(tratamiento.isEstado());
        dto.setTratamiento(tratamientoDto);

        dto.setPrecio(detalle.getPrecio());
        return dto;
    }

    private TarifariosDetalleModel convertToEntity(TarifariosDetalleDto dto) {
        TarifariosDetalleModel detalle = new TarifariosDetalleModel();
        detalle.setPrecio(dto.getPrecio());
        return detalle;
    }
}