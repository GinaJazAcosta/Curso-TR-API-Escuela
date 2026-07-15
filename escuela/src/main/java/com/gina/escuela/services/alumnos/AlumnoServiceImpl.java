package com.gina.escuela.services.alumnos;

import com.gina.escuela.dto.alumno.AlumnoRequest;
import com.gina.escuela.dto.alumno.AlumnoResponse;
import com.gina.escuela.entities.Alumno;
import com.gina.escuela.entities.Inscripcion;
import com.gina.escuela.entities.Maestro;
import com.gina.escuela.exceptions.EntidadRelacionadaException;
import com.gina.escuela.mappers.AlumnoMapper;
import com.gina.escuela.mappers.InscripcionMapper;
import com.gina.escuela.repositories.AlumnoRepository;
import com.gina.escuela.repositories.InscripcionRepository;
import com.gina.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final AlumnoMapper alumnoMapper;
    private final InscripcionRepository inscripcionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoResponse> listar() {
        log.info("Listando todos los maestros");
        return alumnoRepository.findAll().stream()
                .map(alumnoMapper::entidadAResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AlumnoResponse obtenerPorId(Long id) {
        return alumnoMapper.entidadAResponse(obtenerAlumno(id));
    }

    @Override
    public AlumnoResponse registrar(AlumnoRequest request) {

        log.info("Registrando maestro...");
        Alumno alumno = alumnoMapper.requestAEntidad(
                request,
                generarEmail(request),
                generarMatricula(request));
        alumnoRepository.save(alumno);

        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public AlumnoResponse actualizar(AlumnoRequest request, Long id) {
        log.info("Actualizando alumno con id: {}", id);
        Alumno alumno = obtenerAlumno(id);

        if(alumno.cambioEnDatos(request.nombre().trim(), request.apellidoPaterno().trim(), request.apellidoMaterno().trim())){
            alumno.actualizar(
                    request.nombre(),
                    request.apellidoPaterno(),
                    request.apellidoMaterno(),
                    generarEmail(request),
                    generarMatricula(request)
            );
            log.info("Datos académicos generados para el Alumno con id: {}", id);
        }
        alumnoRepository.save(alumno);
        log.info("Alumno con id: {} actualizado", id);
        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public void eliminar(Long id) {
        Alumno alumno = obtenerAlumno(id);
        log.info("Eliminando alumno con id: {}", id);

        if (inscripcionRepository.existsByAlumnoId(id))
            throw new EntidadRelacionadaException("No se puede eliminar al alumno ya que tiene inscripciones asignados");

        alumnoRepository.delete(alumno);
        log.info("Alumno con id: {} eliminado", id);
    }

    private Alumno obtenerAlumno(Long id){
        return ServiceUtils.obtenerEntidadOException(alumnoRepository, id, Alumno.class);
    }

    private String generarEmail(AlumnoRequest request){
        log.info("Generando email...");
        return alumnoRepository.generarCorreo(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim()
        );
    }
    private String generarMatricula(AlumnoRequest request){
        log.info("Generando matricula...");
        return alumnoRepository.generarMatricula(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim()
        );
    }

}
