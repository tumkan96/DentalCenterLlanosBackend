package com.server.DentalCenterLlanos.Controller;

import com.server.DentalCenterLlanos.Dtos.OdontologosDto;
import com.server.DentalCenterLlanos.Dtos.Personas.PersonaDTO;
import com.server.DentalCenterLlanos.Dtos.UsuariosDTO.UsuarioDTO;
import com.server.DentalCenterLlanos.Dtos.EspecialidadesDto;
import com.server.DentalCenterLlanos.Dtos.TratamientosDto;
import com.server.DentalCenterLlanos.Dtos.TarifariosDto;
import com.server.DentalCenterLlanos.Dtos.TarifariosDetalleDto;
import com.server.DentalCenterLlanos.Model.OdontologosModel;
import com.server.DentalCenterLlanos.Model.PersonasModel;
import com.server.DentalCenterLlanos.Repository.OdontologosRepository;
import com.server.DentalCenterLlanos.Repository.PersonasRepository;
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
public class OdontologosController {

    @Autowired
    private OdontologosRepository odontologosRepository;

    @Autowired
    private PersonasRepository personasRepository;

    @GetMapping("/odontologos")
    public ResponseEntity<List<OdontologosDto>> getAllOdontologos() {
        List<OdontologosDto> odontologos = odontologosRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(odontologos, HttpStatus.OK);
    }

    @GetMapping("/odontologos/{id}")
    public ResponseEntity<OdontologosDto> getOdontologoById(@PathVariable Long id) {
        return odontologosRepository.findById(id)
                .map(this::convertToDto)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/odontologos")
    public ResponseEntity<OdontologosDto> createOdontologo(@RequestBody OdontologosDto odontologosDto) {
        PersonasModel persona = personasRepository.findById(odontologosDto.getIdPersona())
                .orElseThrow(() -> new RuntimeException("Persona not found"));
        OdontologosModel odontologo = convertToEntity(odontologosDto);
        odontologo.setPersona(persona);
        OdontologosModel savedOdontologo = odontologosRepository.save(odontologo);
        return new ResponseEntity<>(convertToDto(savedOdontologo), HttpStatus.CREATED);
    }

    @PutMapping("/odontologos/{id}")
    public ResponseEntity<OdontologosDto> updateOdontologo(@PathVariable Long id, @RequestBody OdontologosDto odontologosDto) {
        if (!odontologosRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PersonasModel persona = personasRepository.findById(odontologosDto.getIdPersona())
                .orElseThrow(() -> new RuntimeException("Persona not found"));
        OdontologosModel odontologo = convertToEntity(odontologosDto);
        odontologo.setIdPersona(id);
        odontologo.setPersona(persona);
        OdontologosModel updatedOdontologo = odontologosRepository.save(odontologo);
        return new ResponseEntity<>(convertToDto(updatedOdontologo), HttpStatus.OK);
    }

    @DeleteMapping("/odontologos/{id}")
    public ResponseEntity<Void> deleteOdontologo(@PathVariable Long id) {
        if (!odontologosRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        odontologosRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/cambiarEstadoOdontologo/{id}")
    public ResponseEntity<Object> cambiarEstadoOdontologo(@PathVariable Long id, @RequestParam(required = false) Boolean estado) {
        Optional<OdontologosModel> optionalOdontologo = odontologosRepository.findById(id);
        if (optionalOdontologo.isPresent()) {
            OdontologosModel odontologo = optionalOdontologo.get();
            boolean nuevoEstado = (estado != null) ? estado : !odontologo.isEstado();
            odontologo.setEstado(nuevoEstado);
            odontologosRepository.save(odontologo);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Odontologo " + (nuevoEstado ? "habilitado" : "inhabilitado") + " correctamente.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Odontologo no encontrado.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    private OdontologosDto convertToDto(OdontologosModel odontologo) {
        OdontologosDto dto = new OdontologosDto();
        dto.setIdPersona(odontologo.getIdPersona());
        dto.setNumColegiatura(odontologo.getNumColegiatura());
        dto.setCalificacionPromedio(odontologo.getCalificacionPromedio());
        dto.setEstado(odontologo.isEstado());
        dto.setCorreoElectronico(odontologo.getCorreoElectronico());
        dto.setDireccionDomicilio(odontologo.getDireccionDomicilio());
        // Mapear especialidades como objetos completos
        List<EspecialidadesDto> especialidadesDtos = odontologo.getEspecialidadesAsociadas().stream()
                .map(oe -> {
                    EspecialidadesDto espDto = new EspecialidadesDto();
                    espDto.setIdEspecialidad(oe.getEspecialidad().getIdEspecialidad());
                    espDto.setNombre(oe.getEspecialidad().getNombre());
                    espDto.setDescripcion(oe.getEspecialidad().getDescripcion());
                    espDto.setIdEspecialidadPadre(oe.getEspecialidad().getEspecialidadPadre() != null
                            ? oe.getEspecialidad().getEspecialidadPadre().getIdEspecialidad()
                            : null);
                    espDto.setRequiereInterconsulta(oe.getEspecialidad().isRequiereInterconsulta());
                    espDto.setEstado(oe.getEspecialidad().isEstado());
                    // Mapear tratamientos dentro de especialidad
                    List<TratamientosDto> tratamientosDtos = oe.getEspecialidad().getTratamientosAsociados().stream()
                            .map(et -> {
                                TratamientosDto tDto = new TratamientosDto();
                                tDto.setIdTratamiento(et.getTratamiento().getIdTratamiento());
                                tDto.setNombre(et.getTratamiento().getNombre());
                                tDto.setDescripcion(et.getTratamiento().getDescripcion());
                                tDto.setCodigoCupsCie10(et.getTratamiento().getCodigoCupsCie10());
                                tDto.setEstado(et.getTratamiento().isEstado());
                                // Mapear tarifariosDetalles para incluir el precio
                                List<TarifariosDetalleDto> detallesDtos = et.getTratamiento().getTarifariosDetalles().stream()
                                        .map(td -> {
                                            TarifariosDetalleDto tdDto = new TarifariosDetalleDto();
                                            tdDto.setIdTarifarioDetalle(td.getIdTarifarioDetalle());
                                            tdDto.setPrecio(td.getPrecio());
                                            // Mapear Tarifario (sin detalles para evitar ciclos)
                                            TarifariosDto tarifarioDto = new TarifariosDto();
                                            tarifarioDto.setIdTarifario(td.getTarifario().getIdTarifario());
                                            tarifarioDto.setNombre(td.getTarifario().getNombre());
                                            tarifarioDto.setFechaVigencia(td.getTarifario().getFechaVigencia());
                                            tarifarioDto.setEstado(td.getTarifario().isEstado());
                                            tdDto.setTarifario(tarifarioDto);
                                            return tdDto;
                                        })
                                        .collect(Collectors.toList());
                                tDto.setTarifariosDetalles(detallesDtos);
                                return tDto;
                            })
                            .collect(Collectors.toList());
                    espDto.setTratamientos(tratamientosDtos);
                    return espDto;
                })
                .collect(Collectors.toList());
        dto.setEspecialidades(especialidadesDtos);
        // Mapear los datos de PersonasModel a PersonaDTO
        PersonasModel persona = odontologo.getPersona();
        PersonaDTO personaDto = new PersonaDTO();
        personaDto.setIdPersona(persona.getIdPersona());
        personaDto.setCedulaIdentidad(persona.getCedulaIdentidad());
        personaDto.setNombres(persona.getNombres());
        personaDto.setApellidoPaterno(persona.getApellidoPaterno());
        personaDto.setApellidoMaterno(persona.getApellidoMaterno());
        personaDto.setFotografia(persona.getFotografia());
        personaDto.setTelefonoCelular(persona.getTelefonoCelular());
        personaDto.setEstado(persona.isEstado());
        // Mapear el UsuarioModel a UsuarioDTO (si existe)
        if (persona.getUsuario() != null) {
            UsuarioDTO usuarioDto = new UsuarioDTO();
            // usuarioDto.setIdUsuario(persona.getUsuario().getIdUsuario());
            // Agrega otros campos de UsuarioDTO según su definición
            personaDto.setUsuario(usuarioDto);
        }
        dto.setPersona(personaDto);
        return dto;
    }

    private OdontologosModel convertToEntity(OdontologosDto dto) {
        OdontologosModel odontologo = new OdontologosModel();
        odontologo.setNumColegiatura(dto.getNumColegiatura());
        odontologo.setCalificacionPromedio(dto.getCalificacionPromedio());
        odontologo.setEstado(dto.isEstado());
        odontologo.setCorreoElectronico(dto.getCorreoElectronico());
        odontologo.setDireccionDomicilio(dto.getDireccionDomicilio());
        // La relación con especialidades no se maneja aquí, ya que se gestiona en OdontologosEspecialidadesController
        return odontologo;
    }
}