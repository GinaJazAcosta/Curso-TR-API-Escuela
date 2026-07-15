package com.gina.escuela.dto.calificacion;

import com.gina.escuela.dto.inscripcion.InscripcionResponse;

import java.util.List;

public record CalificacionResponse(
        Long id,
        InscripcionResponse inscripcion,
        Integer calificaion,
        String fecha //LocalDate
) {
}
