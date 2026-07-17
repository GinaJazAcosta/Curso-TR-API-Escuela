package com.gina.escuela.mappers;

import com.gina.escuela.dto.calificacion.CalificacionRequest;
import com.gina.escuela.dto.calificacion.CalificacionResponse;
import com.gina.escuela.dto.datos.*;
import com.gina.escuela.entities.*;
import com.gina.escuela.utils.StringCustomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class CalificacionMapper implements CommonMapper<CalificacionRequest, CalificacionResponse, Calificacion>{

    @Override
    public Calificacion requestAEntidad(CalificacionRequest request) {
        if (request == null) return null;
        return Calificacion.builder()
                .calificacion(request.calificacion())
                .build();
    }

    public Calificacion requestAEntidad(CalificacionRequest request, Inscripcion inscripcion, LocalDate fechaRegistro) {
        if (request == null) return null;
        Calificacion calificacion = requestAEntidad(request);
        calificacion.actualizar(inscripcion);
        return calificacion;
    }






    @Override
    public CalificacionResponse entidadAResponse(Calificacion entidad) {
        if (entidad == null) return null;
        return new CalificacionResponse(
                entidad.getId(),
                entidadADatosInscripcion(entidad),
                entidad.getCalificacion(),
                StringCustomUtils.localDateString(entidad.getFechaRegistro())
        );
    }

    private DatosInscripcion entidadADatosInscripcion(Calificacion entidad) {
        if (entidad == null) return null;
        Inscripcion inscripcion = entidad.getInscripcion();
        return new DatosInscripcion(
                getDatosAlumno(inscripcion),
                getDatosGrupo(inscripcion),
                StringCustomUtils.localDateString(inscripcion.getFechaInscripcion())
        );
    }

    private DatosAlumno getDatosAlumno(Inscripcion entidad) {
        if (entidad == null) return null;
        Alumno alumno = entidad.getAlumno();
        return new DatosAlumno(
                alumno.getNombreCompleto(),
                alumno.getMatricula(),
                alumno.getEmail(),
                StringCustomUtils.localDateString(alumno.getFechaIngreso())
        );
    }

    private DatosGrupo getDatosGrupo(Inscripcion entidad) {
        if (entidad == null) return null;
        Grupo grupo = entidad.getGrupo();
        return new DatosGrupo(
                grupo.getCurso().getNombre(),
                grupo.getMaestro().getNombreCompleto(),
                grupo.getAula().getNombre(),
                grupo.getPeriodo()
        );
    }

}
