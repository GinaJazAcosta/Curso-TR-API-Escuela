package com.gina.escuela.mappers;

import com.gina.escuela.dto.alumno.AlumnoRequest;
import com.gina.escuela.dto.alumno.AlumnoResponse;
import com.gina.escuela.dto.datos.DatosCalificaciones;
import com.gina.escuela.entities.Alumno;
import com.gina.escuela.utils.StringCustomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AlumnoMapper implements CommonMapper<AlumnoRequest, AlumnoResponse, Alumno> {

    @Override
    public Alumno requestAEntidad(AlumnoRequest request) {
        if (request == null) return null;
        return Alumno.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .fechaIngreso(LocalDate.now())
                .build();
    }

    public Alumno requestAEntidad(AlumnoRequest request, String email, String matricula) {
        if (request == null) return null;
        Alumno alumno = requestAEntidad(request);
        alumno.asignarDatosAcademicos(email, matricula);
        return alumno;
    }

    @Override
    public AlumnoResponse entidadAResponse(Alumno entidad) {
        if (entidad == null) return null;

        List<DatosCalificaciones> calificaciones = entidadADatosCalificaciones(entidad);

        return new AlumnoResponse(
                entidad.getId(),
                entidad.getNombreCompleto(),
                entidad.getEmail(),
                entidad.getMatricula(),
                StringCustomUtils.localDateString(entidad.getFechaIngreso()),
                calificaciones,
                entidad.calcularPromemedio()
        );
    }


    private List<DatosCalificaciones> entidadADatosCalificaciones(Alumno entidad) {
        if (entidad == null || entidad.getInscripciones() == null || entidad.getInscripciones().isEmpty())
            return List.of();
        return entidad.getInscripciones().stream()
                .map(inscripcion -> new DatosCalificaciones(
                        inscripcion.getGrupo().getCurso().getNombre(),
                        inscripcion.getGrupo().getPeriodo(),
                        inscripcion.getCalificacion() != null
                                ? inscripcion.getCalificacion().getCalificacion()
                                : null
                )).toList();
    }

}
