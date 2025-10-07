package com.server.DentalCenterLlanos.Controller;

import com.server.DentalCenterLlanos.Dtos.EspecialidadesDto;
import com.server.DentalCenterLlanos.Dtos.OdontologosDto;
import com.server.DentalCenterLlanos.Dtos.TratamientosDto;
import com.server.DentalCenterLlanos.Model.EspecialidadesModel;
import com.server.DentalCenterLlanos.Repository.EspecialidadesRepository;
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
public class EspecialidadesController {

    @Autowired
    private EspecialidadesRepository especialidadesRepository;

    @GetMapping("/especialidades")
    public ResponseEntity<List<EspecialidadesDto>> getAllEspecialidades() {
        List<EspecialidadesDto> especialidades = especialidadesRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(especialidades, HttpStatus.OK);
    }

    @GetMapping("/especialidades/{id}")
    public ResponseEntity<EspecialidadesDto> getEspecialidadById(@PathVariable Long id) {
        return especialidadesRepository.findById(id)
                .map(this::convertToDto)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/especialidades")
    public ResponseEntity<EspecialidadesDto> createEspecialidad(@RequestBody EspecialidadesDto especialidadesDto) {
        EspecialidadesModel especialidad = convertToEntity(especialidadesDto);
        if (especialidadesDto.getIdEspecialidadPadre() != null) {
            EspecialidadesModel parent = especialidadesRepository.findById(especialidadesDto.getIdEspecialidadPadre())
                    .orElseThrow(() -> new RuntimeException("Especialidad padre not found"));
            especialidad.setEspecialidadPadre(parent);
        }
        EspecialidadesModel savedEspecialidad = especialidadesRepository.save(especialidad);
        return new ResponseEntity<>(convertToDto(savedEspecialidad), HttpStatus.CREATED);
    }

    @PutMapping("/especialidades/{id}")
    public ResponseEntity<EspecialidadesDto> updateEspecialidad(@PathVariable Long id, @RequestBody EspecialidadesDto especialidadesDto) {
        if (!especialidadesRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EspecialidadesModel especialidad = convertToEntity(especialidadesDto);
        especialidad.setIdEspecialidad(id);
        if (especialidadesDto.getIdEspecialidadPadre() != null) {
            EspecialidadesModel parent = especialidadesRepository.findById(especialidadesDto.getIdEspecialidadPadre())
                    .orElseThrow(() -> new RuntimeException("Especialidad padre not found"));
            especialidad.setEspecialidadPadre(parent);
        }
        EspecialidadesModel updatedEspecialidad = especialidadesRepository.save(especialidad);
        return new ResponseEntity<>(convertToDto(updatedEspecialidad), HttpStatus.OK);
    }

    @DeleteMapping("/especialidades/{id}")
    public ResponseEntity<Void> deleteEspecialidad(@PathVariable Long id) {
        if (!especialidadesRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        especialidadesRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/cambiarEstadoEspecialidad/{id}")
    public ResponseEntity<Object> cambiarEstadoEspecialidad(@PathVariable Long id, @RequestParam(required = false) Boolean estado) {
        Optional<EspecialidadesModel> optionalEspecialidad = especialidadesRepository.findById(id);
        if (optionalEspecialidad.isPresent()) {
            EspecialidadesModel especialidad = optionalEspecialidad.get();
            boolean nuevoEstado = (estado != null) ? estado : !especialidad.isEstado();
            especialidad.setEstado(nuevoEstado);
            especialidadesRepository.save(especialidad);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Especialidad " + (nuevoEstado ? "habilitada" : "inhabilitada") + " correctamente.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Especialidad no encontrada.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    private EspecialidadesDto convertToDto(EspecialidadesModel especialidad) {
        EspecialidadesDto dto = new EspecialidadesDto();
        dto.setIdEspecialidad(especialidad.getIdEspecialidad());
        dto.setNombre(especialidad.getNombre());
        dto.setDescripcion(especialidad.getDescripcion());
        dto.setIdEspecialidadPadre(especialidad.getEspecialidadPadre() != null
                ? especialidad.getEspecialidadPadre().getIdEspecialidad()
                : null);
        dto.setRequiereInterconsulta(especialidad.isRequiereInterconsulta());
        dto.setEstado(especialidad.isEstado());

        // Mapear odontologos como objetos completos
        List<OdontologosDto> odontologosDtos = especialidad.getOdontologosAsociados().stream()
                .map(oe -> {
                    OdontologosDto oDto = new OdontologosDto();
                    oDto.setIdPersona(oe.getOdontologo().getIdPersona());
                    oDto.setNumColegiatura(oe.getOdontologo().getNumColegiatura());
                    oDto.setCalificacionPromedio(oe.getOdontologo().getCalificacionPromedio());
                    oDto.setEstado(oe.getOdontologo().isEstado());
                    oDto.setCorreoElectronico(oe.getOdontologo().getCorreoElectronico());
                    oDto.setDireccionDomicilio(oe.getOdontologo().getDireccionDomicilio());
                    return oDto;
                })
                .collect(Collectors.toList());
        dto.setOdontologos(odontologosDtos);

        // Mapear tratamientos como objetos completos
        List<TratamientosDto> tratamientosDtos = especialidad.getTratamientosAsociados().stream()
                .map(et -> {
                    TratamientosDto tDto = new TratamientosDto();
                    tDto.setIdTratamiento(et.getTratamiento().getIdTratamiento());
                    tDto.setNombre(et.getTratamiento().getNombre());
                    tDto.setDescripcion(et.getTratamiento().getDescripcion());
                    tDto.setCodigoCupsCie10(et.getTratamiento().getCodigoCupsCie10());
                    tDto.setEstado(et.getTratamiento().isEstado());
                    return tDto;
                })
                .collect(Collectors.toList());
        dto.setTratamientos(tratamientosDtos);

        return dto;
    }

    private EspecialidadesModel convertToEntity(EspecialidadesDto dto) {
        EspecialidadesModel especialidad = new EspecialidadesModel();
        especialidad.setNombre(dto.getNombre());
        especialidad.setDescripcion(dto.getDescripcion());
        especialidad.setRequiereInterconsulta(dto.isRequiereInterconsulta());
        especialidad.setEstado(dto.isEstado());
        return especialidad;
    }
}