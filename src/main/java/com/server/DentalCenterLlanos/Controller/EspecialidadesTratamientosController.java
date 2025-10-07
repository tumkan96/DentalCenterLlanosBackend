package com.server.DentalCenterLlanos.Controller;

import com.server.DentalCenterLlanos.Dtos.EspecialidadesTratamientosDto;
import com.server.DentalCenterLlanos.Dtos.EspecialidadesDto;
import com.server.DentalCenterLlanos.Dtos.TratamientosDto;
import com.server.DentalCenterLlanos.Model.EspecialidadesTratamientosModel;
import com.server.DentalCenterLlanos.Model.EspecialidadesTratamientosId;
import com.server.DentalCenterLlanos.Model.EspecialidadesModel;
import com.server.DentalCenterLlanos.Model.TratamientosModel;
import com.server.DentalCenterLlanos.Repository.EspecialidadesTratamientosRepository;
import com.server.DentalCenterLlanos.Repository.EspecialidadesRepository;
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
public class EspecialidadesTratamientosController {

    @Autowired
    private EspecialidadesTratamientosRepository especialidadesTratamientosRepository;

    @Autowired
    private EspecialidadesRepository especialidadesRepository;

    @Autowired
    private TratamientosRepository tratamientosRepository;

    @GetMapping("/especialidades-tratamientos")
    public ResponseEntity<List<EspecialidadesTratamientosDto>> getAllEspecialidadesTratamientos() {
        List<EspecialidadesTratamientosDto> relaciones = especialidadesTratamientosRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(relaciones, HttpStatus.OK);
    }

    @PostMapping("/especialidades-tratamientos")
    public ResponseEntity<EspecialidadesTratamientosDto> createEspecialidadTratamiento(@RequestBody EspecialidadesTratamientosDto dto) {
        EspecialidadesModel especialidad = especialidadesRepository.findById(dto.getEspecialidad().getIdEspecialidad())
                .orElseThrow(() -> new RuntimeException("Especialidad not found"));
        TratamientosModel tratamiento = tratamientosRepository.findById(dto.getTratamiento().getIdTratamiento())
                .orElseThrow(() -> new RuntimeException("Tratamiento not found"));
        EspecialidadesTratamientosModel relacion = convertToEntity(dto);
        relacion.setEspecialidad(especialidad);
        relacion.setTratamiento(tratamiento);
        EspecialidadesTratamientosModel savedRelacion = especialidadesTratamientosRepository.save(relacion);
        return new ResponseEntity<>(convertToDto(savedRelacion), HttpStatus.CREATED);
    }

    @DeleteMapping("/especialidades-tratamientos/{idEspecialidad}/{idTratamiento}")
    public ResponseEntity<Void> deleteEspecialidadTratamiento(@PathVariable Long idEspecialidad, @PathVariable Long idTratamiento) {
        EspecialidadesTratamientosId id = new EspecialidadesTratamientosId();
        id.setIdEspecialidad(idEspecialidad);
        id.setIdTratamiento(idTratamiento);
        if (!especialidadesTratamientosRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        especialidadesTratamientosRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private EspecialidadesTratamientosDto convertToDto(EspecialidadesTratamientosModel relacion) {
        EspecialidadesTratamientosDto dto = new EspecialidadesTratamientosDto();
        dto.setId(relacion.getId());

        // Mapear EspecialidadesDto
        EspecialidadesModel especialidad = relacion.getEspecialidad();
        EspecialidadesDto especialidadDto = new EspecialidadesDto();
        especialidadDto.setIdEspecialidad(especialidad.getIdEspecialidad());
        especialidadDto.setNombre(especialidad.getNombre());
        especialidadDto.setDescripcion(especialidad.getDescripcion());
        especialidadDto.setIdEspecialidadPadre(especialidad.getEspecialidadPadre() != null
                ? especialidad.getEspecialidadPadre().getIdEspecialidad()
                : null);
        especialidadDto.setRequiereInterconsulta(especialidad.isRequiereInterconsulta());
        especialidadDto.setEstado(especialidad.isEstado());
        dto.setEspecialidad(especialidadDto);

        // Mapear TratamientosDto
        TratamientosModel tratamiento = relacion.getTratamiento();
        TratamientosDto tratamientoDto = new TratamientosDto();
        tratamientoDto.setIdTratamiento(tratamiento.getIdTratamiento());
        tratamientoDto.setNombre(tratamiento.getNombre());
        tratamientoDto.setDescripcion(tratamiento.getDescripcion());
        tratamientoDto.setCodigoCupsCie10(tratamiento.getCodigoCupsCie10());
        tratamientoDto.setEstado(tratamiento.isEstado());
        dto.setTratamiento(tratamientoDto);

        return dto;
    }

    private EspecialidadesTratamientosModel convertToEntity(EspecialidadesTratamientosDto dto) {
        EspecialidadesTratamientosModel relacion = new EspecialidadesTratamientosModel();
        EspecialidadesTratamientosId id = new EspecialidadesTratamientosId();
        id.setIdEspecialidad(dto.getEspecialidad().getIdEspecialidad());
        id.setIdTratamiento(dto.getTratamiento().getIdTratamiento());
        relacion.setId(id);
        return relacion;
    }
}