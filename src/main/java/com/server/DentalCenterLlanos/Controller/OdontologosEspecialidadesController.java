package com.server.DentalCenterLlanos.Controller;

import com.server.DentalCenterLlanos.Dtos.OdontologosEspecialidadesDto;
import com.server.DentalCenterLlanos.Dtos.OdontologosDto;
import com.server.DentalCenterLlanos.Dtos.EspecialidadesDto;
import com.server.DentalCenterLlanos.Model.OdontologosEspecialidadesModel;
import com.server.DentalCenterLlanos.Model.OdontologosEspecialidadesId;
import com.server.DentalCenterLlanos.Model.OdontologosModel;
import com.server.DentalCenterLlanos.Model.EspecialidadesModel;
import com.server.DentalCenterLlanos.Repository.OdontologosEspecialidadesRepository;
import com.server.DentalCenterLlanos.Repository.OdontologosRepository;
import com.server.DentalCenterLlanos.Repository.EspecialidadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RestController
public class OdontologosEspecialidadesController {

    @Autowired
    private OdontologosEspecialidadesRepository odontologosEspecialidadesRepository;

    @Autowired
    private OdontologosRepository odontologosRepository;

    @Autowired
    private EspecialidadesRepository especialidadesRepository;

    @GetMapping("/odontologos-especialidades")
    public ResponseEntity<List<OdontologosEspecialidadesDto>> getAllOdontologosEspecialidades() {
        List<OdontologosEspecialidadesDto> relaciones = odontologosEspecialidadesRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(relaciones, HttpStatus.OK);
    }

    @PostMapping("/odontologos-especialidades")
    public ResponseEntity<OdontologosEspecialidadesDto> createOdontologoEspecialidad(@RequestBody OdontologosEspecialidadesDto dto) {
        OdontologosModel odontologo = odontologosRepository.findById(dto.getOdontologo().getIdPersona())
                .orElseThrow(() -> new RuntimeException("Odontologo not found"));
        EspecialidadesModel especialidad = especialidadesRepository.findById(dto.getEspecialidad().getIdEspecialidad())
                .orElseThrow(() -> new RuntimeException("Especialidad not found"));
        OdontologosEspecialidadesModel relacion = convertToEntity(dto);
        relacion.setOdontologo(odontologo);
        relacion.setEspecialidad(especialidad);
        OdontologosEspecialidadesModel savedRelacion = odontologosEspecialidadesRepository.save(relacion);
        return new ResponseEntity<>(convertToDto(savedRelacion), HttpStatus.CREATED);
    }

    @DeleteMapping("/odontologos-especialidades/{idOdontologo}/{idEspecialidad}")
    public ResponseEntity<Void> deleteOdontologoEspecialidad(@PathVariable Long idOdontologo, @PathVariable Long idEspecialidad) {
        OdontologosEspecialidadesId id = new OdontologosEspecialidadesId();
        id.setIdOdontologo(idOdontologo);
        id.setIdEspecialidad(idEspecialidad);
        if (!odontologosEspecialidadesRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        odontologosEspecialidadesRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private OdontologosEspecialidadesDto convertToDto(OdontologosEspecialidadesModel relacion) {
        OdontologosEspecialidadesDto dto = new OdontologosEspecialidadesDto();
        dto.setId(relacion.getId());

        // Mapear OdontologosDto
        OdontologosModel odontologo = relacion.getOdontologo();
        OdontologosDto odontologoDto = new OdontologosDto();
        odontologoDto.setIdPersona(odontologo.getIdPersona());
        odontologoDto.setNumColegiatura(odontologo.getNumColegiatura());
        odontologoDto.setCalificacionPromedio(odontologo.getCalificacionPromedio());
        odontologoDto.setEstado(odontologo.isEstado());
        odontologoDto.setCorreoElectronico(odontologo.getCorreoElectronico());
        odontologoDto.setDireccionDomicilio(odontologo.getDireccionDomicilio());
        dto.setOdontologo(odontologoDto);

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

        return dto;
    }

    private OdontologosEspecialidadesModel convertToEntity(OdontologosEspecialidadesDto dto) {
        OdontologosEspecialidadesModel relacion = new OdontologosEspecialidadesModel();
        OdontologosEspecialidadesId id = new OdontologosEspecialidadesId();
        id.setIdOdontologo(dto.getOdontologo().getIdPersona());
        id.setIdEspecialidad(dto.getEspecialidad().getIdEspecialidad());
        relacion.setId(id);
        return relacion;
    }
}