package com.gina.escuela.mappers;

import com.gina.escuela.dto.datos.DatosAlumno;
import com.gina.escuela.dto.datos.DatosGrupo;
import com.gina.escuela.dto.inscripcion.InscripcionRequest;
import com.gina.escuela.dto.inscripcion.InscripcionResponse;
import com.gina.escuela.entities.*;
import com.gina.escuela.utils.StringCustomUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class InscripcionMapper implements CommonMapper<InscripcionRequest, InscripcionResponse, Inscripcion>{

    @Override
    public Inscripcion requestAEntidad(InscripcionRequest request) {
        if (request == null) return null;
        return Inscripcion.builder().build();
    }

    public Inscripcion requestAEntidad(InscripcionRequest request, Alumno alumno, Grupo grupo, LocalDate fechaInscripcion, Calificacion calificacion) {
        if (request == null) return null;
        Inscripcion inscripcion = requestAEntidad(request);
        inscripcion.asignarDatos(alumno, grupo, fechaInscripcion, calificacion);
        return inscripcion;
    }












    @Override
    public InscripcionResponse entidadAResponse(Inscripcion entidad) {
        if (entidad == null) return null;
        BigDecimal calificacion = entidad.getCalificacion() == null
                ? null
                : entidad.getCalificacion().getCalificacion();
        return new InscripcionResponse(
                entidad.getId(),
                getDatosAlumno(entidad),
                getDatosGrupo(entidad),
                calificacion,
                StringCustomUtils.localDateString(entidad.getFechaInscripcion())
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
