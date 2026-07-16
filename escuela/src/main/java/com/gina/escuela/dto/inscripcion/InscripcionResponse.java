package com.gina.escuela.dto.inscripcion;

import com.gina.escuela.dto.datos.DatosAlumno;
import com.gina.escuela.dto.datos.DatosGrupo;

import java.math.BigDecimal;

public record InscripcionResponse(
        Long id,
        DatosAlumno alumno,
        DatosGrupo grupo,
        BigDecimal calificacion,
        String fechaInscripcion
) {
}
