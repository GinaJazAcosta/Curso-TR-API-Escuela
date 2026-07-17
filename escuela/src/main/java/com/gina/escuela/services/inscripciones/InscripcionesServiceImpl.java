package com.gina.escuela.services.inscripciones;

import com.gina.escuela.dto.inscripcion.InscripcionRequest;
import com.gina.escuela.dto.inscripcion.InscripcionResponse;
import com.gina.escuela.entities.Alumno;
import com.gina.escuela.entities.Grupo;
import com.gina.escuela.entities.Inscripcion;
import com.gina.escuela.exceptions.EntidadRelacionadaException;
import com.gina.escuela.mappers.InscripcionMapper;
import com.gina.escuela.repositories.AlumnoRepository;
import com.gina.escuela.repositories.CalifiacionRepository;
import com.gina.escuela.repositories.GrupoRepository;
import com.gina.escuela.repositories.InscripcionRepository;
import com.gina.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class InscripcionesServiceImpl implements InscripcionService{

    private final InscripcionRepository inscripcionRepository;
    private final InscripcionMapper inscripcionMapper;
    private final CalifiacionRepository califiacionRepository;

    private final AlumnoRepository alumnoRepository;
    private final GrupoRepository grupoRepository;

    @Override
    public List<InscripcionResponse> listar() {
        log.info("Listando todos las inscripciones");
        return inscripcionRepository.findAll().stream()
                .map(inscripcionMapper::entidadAResponse).toList();
    }

    @Override
    public InscripcionResponse obtenerPorId(Long id) {
        return inscripcionMapper.entidadAResponse(obtenerInscripcion(id));
    }

    @Override
    public InscripcionResponse registrar(InscripcionRequest request) {

        log.info("Validando datos de la inscripción");

        Alumno alumno = ServiceUtils.obtenerEntidadOException(alumnoRepository, request.idAlumno(), Alumno.class);
        Grupo grupo = ServiceUtils.obtenerEntidadOException(grupoRepository, request.idGrupo(), Grupo.class);

        if (inscripcionRepository.existeInscripcion(request.idAlumno(), request.idGrupo()) > 0)
            throw new IllegalArgumentException("El alumno ya se encuentra inscrito en el grupo");

        log.info("Registrando inscripción");
        Inscripcion inscripcion = inscripcionMapper.requestAEntidad(request, alumno, grupo, LocalDate.now(), null);

        inscripcionRepository.save(inscripcion);
        log.info("Inscripción creada correctamente");
        return inscripcionMapper.entidadAResponse(inscripcion);
    }

    @Override
    public InscripcionResponse actualizar(InscripcionRequest request, Long id) {

        Inscripcion inscripcion = obtenerInscripcion(id);
        Alumno alumno = ServiceUtils.obtenerEntidadOException(alumnoRepository, request.idAlumno(), Alumno.class);
        Grupo grupo = ServiceUtils.obtenerEntidadOException(grupoRepository, request.idGrupo(), Grupo.class);

        if (inscripcionRepository.existeInscripcionActualizar(request.idAlumno(), request.idGrupo(), id) > 0)
            throw new IllegalArgumentException("Ya existe una inscripción para ese alumno y grupo");

        inscripcion.asignarDatos(alumno, grupo, inscripcion.getFechaInscripcion(), inscripcion.getCalificacion());

        inscripcionRepository.save(inscripcion);
        log.info("Inscripción actualizada correctamente");
        return inscripcionMapper.entidadAResponse(inscripcion);
    }

    @Override
    public void eliminar(Long id) {
        Inscripcion inscripcion = obtenerInscripcion(id);
        log.info("Eliminando inscripción con id: {}", id);

        if (califiacionRepository.existsByInscripcionId(id))
            throw new EntidadRelacionadaException("No se puede eliminar la inscripción ya que tiene calificaciones asignados");

        inscripcionRepository.delete(inscripcion);
        log.info("Inscripción con id: {} eliminado", id);
    }

    private Inscripcion obtenerInscripcion(Long id){
        return ServiceUtils.obtenerEntidadOException(inscripcionRepository, id, Inscripcion.class);
    }
}
