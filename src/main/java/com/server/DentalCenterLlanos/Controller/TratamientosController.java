package com.server.DentalCenterLlanos.Controller;

import com.server.DentalCenterLlanos.Dtos.TratamientosDto;
import com.server.DentalCenterLlanos.Dtos.EspecialidadesDto;
import com.server.DentalCenterLlanos.Dtos.TarifariosDto;
import com.server.DentalCenterLlanos.Dtos.TarifariosDetalleDto;
import com.server.DentalCenterLlanos.Model.TratamientosModel;
import com.server.DentalCenterLlanos.Model.EspecialidadesTratamientosModel;
import com.server.DentalCenterLlanos.Model.TarifariosDetalleModel;
import com.server.DentalCenterLlanos.Repository.TratamientosRepository;
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
public class TratamientosController {

    @Autowired
    private TratamientosRepository tratamientosRepository;

    @GetMapping("/tratamientos")
    public ResponseEntity<List<TratamientosDto>> getAllTratamientos() {
        List<TratamientosDto> tratamientos = tratamientosRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(tratamientos, HttpStatus.OK);
    }

    @GetMapping("/tratamientos/{id}")
    public ResponseEntity<TratamientosDto> getTratamientoById(@PathVariable Long id) {
        return tratamientosRepository.findById(id)
                .map(this::convertToDto)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/tratamientos")
    public ResponseEntity<TratamientosDto> createTratamiento(@RequestBody TratamientosDto tratamientosDto) {
        TratamientosModel tratamiento = convertToEntity(tratamientosDto);
        TratamientosModel savedTratamiento = tratamientosRepository.save(tratamiento);
        return new ResponseEntity<>(convertToDto(savedTratamiento), HttpStatus.CREATED);
    }

    @PutMapping("/tratamientos/{id}")
    public ResponseEntity<TratamientosDto> updateTratamiento(@PathVariable Long id, @RequestBody TratamientosDto tratamientosDto) {
        if (!tratamientosRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TratamientosModel tratamiento = convertToEntity(tratamientosDto);
        tratamiento.setIdTratamiento(id);
        TratamientosModel updatedTratamiento = tratamientosRepository.save(tratamiento);
        return new ResponseEntity<>(convertToDto(updatedTratamiento), HttpStatus.OK);
    }

    @DeleteMapping("/tratamientos/{id}")
    public ResponseEntity<Void> deleteTratamiento(@PathVariable Long id) {
        if (!tratamientosRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        tratamientosRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/cambiarEstadoTratamiento/{id}")
    public ResponseEntity<Object> cambiarEstadoTratamiento(@PathVariable Long id, @RequestParam(required = false) Boolean estado) {
        Optional<TratamientosModel> optionalTratamiento = tratamientosRepository.findById(id);
        if (optionalTratamiento.isPresent()) {
            TratamientosModel tratamiento = optionalTratamiento.get();
            boolean nuevoEstado = (estado != null) ? estado : !tratamiento.isEstado();
            tratamiento.setEstado(nuevoEstado);
            tratamientosRepository.save(tratamiento);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Tratamiento " + (nuevoEstado ? "habilitado" : "inhabilitado") + " correctamente.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Tratamiento no encontrado.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    private TratamientosDto convertToDto(TratamientosModel tratamiento) {
        TratamientosDto dto = new TratamientosDto();
        dto.setIdTratamiento(tratamiento.getIdTratamiento());
        dto.setNombre(tratamiento.getNombre());
        dto.setDescripcion(tratamiento.getDescripcion());
        dto.setCodigoCupsCie10(tratamiento.getCodigoCupsCie10());
        dto.setEstado(tratamiento.isEstado());

        // Mapear especialidades como objetos completos
        List<EspecialidadesDto> especialidadesDtos = tratamiento.getEspecialidadesAsociadas().stream()
                .map((EspecialidadesTratamientosModel et) -> {
                    EspecialidadesDto espDto = new EspecialidadesDto();
                    espDto.setIdEspecialidad(et.getEspecialidad().getIdEspecialidad());
                    espDto.setNombre(et.getEspecialidad().getNombre());
                    espDto.setDescripcion(et.getEspecialidad().getDescripcion());
                    espDto.setIdEspecialidadPadre(et.getEspecialidad().getEspecialidadPadre() != null
                            ? et.getEspecialidad().getEspecialidadPadre().getIdEspecialidad()
                            : null);
                    espDto.setRequiereInterconsulta(et.getEspecialidad().isRequiereInterconsulta());
                    espDto.setEstado(et.getEspecialidad().isEstado());
                    return espDto;
                })
                .collect(Collectors.toList());
        dto.setEspecialidades(especialidadesDtos);

        // Mapear tarifariosDetalles como objetos completos
        List<TarifariosDetalleDto> detallesDtos = tratamiento.getTarifariosDetalles().stream()
                .map((TarifariosDetalleModel td) -> {
                    TarifariosDetalleDto tdDto = new TarifariosDetalleDto();
                    tdDto.setIdTarifarioDetalle(td.getIdTarifarioDetalle());
                    tdDto.setPrecio(td.getPrecio());
                    // Mapear Tarifario
                    TarifariosDto tarifarioDto = new TarifariosDto();
                    tarifarioDto.setIdTarifario(td.getTarifario().getIdTarifario());
                    tarifarioDto.setNombre(td.getTarifario().getNombre());
                    tarifarioDto.setFechaVigencia(td.getTarifario().getFechaVigencia());
                    tarifarioDto.setEstado(td.getTarifario().isEstado());
                    tdDto.setTarifario(tarifarioDto);
                    return tdDto;
                })
                .collect(Collectors.toList());
        dto.setTarifariosDetalles(detallesDtos);

        return dto;
    }

    private TratamientosModel convertToEntity(TratamientosDto dto) {
        TratamientosModel tratamiento = new TratamientosModel();
        tratamiento.setNombre(dto.getNombre());
        tratamiento.setDescripcion(dto.getDescripcion());
        tratamiento.setCodigoCupsCie10(dto.getCodigoCupsCie10());
        tratamiento.setEstado(dto.isEstado());
        return tratamiento;
    }
}