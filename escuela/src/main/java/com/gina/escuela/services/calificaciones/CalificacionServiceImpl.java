package com.gina.escuela.services.calificaciones;

import com.gina.escuela.dto.calificacion.CalificacionRequest;
import com.gina.escuela.dto.calificacion.CalificacionResponse;
import com.gina.escuela.entities.Calificacion;
import com.gina.escuela.entities.Inscripcion;
import com.gina.escuela.mappers.CalificacionMapper;
import com.gina.escuela.repositories.CalifiacionRepository;
import com.gina.escuela.repositories.InscripcionRepository;
import com.gina.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor // @RequiredArgsConstructor
@Transactional
@Slf4j
public class CalificacionServiceImpl implements CalificacionService{

    private final CalifiacionRepository califiacionRepository;
    private final CalificacionMapper calificacionMapper;

    private final InscripcionRepository inscripcionRepository;

    @Override
    public List<CalificacionResponse> listar() {
        log.info("Listando todos las calificaciones");
        return califiacionRepository.findAll().stream()
                .map(calificacionMapper::entidadAResponse).toList();
    }

    @Override
    public CalificacionResponse obtenerPorId(Long id) {
        return calificacionMapper.entidadAResponse(obtenerCalificacion(id));
    }

    @Override
    public CalificacionResponse registrar(CalificacionRequest request) {

        log.info("Validando datos de la calificación");
        Inscripcion inscripcion = ServiceUtils.obtenerEntidadOException(inscripcionRepository, request.idInscripcion(), Inscripcion.class);

        if (califiacionRepository.existeCalificacion(request.idInscripcion()) > 0)
            throw new IllegalArgumentException("La inscripción ya cuenta con una calificación registrada");

        log.info("Registrando calificación");
        Calificacion calificacion = calificacionMapper.requestAEntidad(request, inscripcion, LocalDate.now());

        califiacionRepository.save(calificacion);
        log.info("Calificación registrada correctamente");
        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public CalificacionResponse actualizar(CalificacionRequest request, Long id) {

        Calificacion calificacion = obtenerCalificacion(id);
        Inscripcion inscripcion = ServiceUtils.obtenerEntidadOException(inscripcionRepository, request.idInscripcion(), Inscripcion.class);

        if (califiacionRepository.existeCalificacionActualizar(request.idInscripcion(), id) > 0)
            throw new IllegalArgumentException("La inscripción ya cuenta con una calificación registrada");

        calificacion.actualizar(inscripcion);
        calificacion.asignarCalificacion(request.calificacion());
        califiacionRepository.save(calificacion);

        log.info("Calificación actualizada correctamente");

        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public void eliminar(Long id) {
        Calificacion calificacion = obtenerCalificacion(id);
        log.info("Eliminando calificación con id: {}", id);

        califiacionRepository.delete(calificacion);
        log.info("Calificacion con id: {} eliminado", id);
    }

    private Calificacion obtenerCalificacion(Long id){
        return ServiceUtils.obtenerEntidadOException(califiacionRepository, id, Calificacion.class);
    }
}
