package com.gina.escuela.dto.inscripcion;

import com.gina.escuela.dto.alumno.AlumnoResponse;
import com.gina.escuela.dto.datos.DatosCalificaciones;

import java.util.List;

public record InscripcionResponse(
        Long id,
        AlumnoResponse alumno,
        String telefono,
        List<DatosCalificaciones> calificaciones
) {
}
