package com.gina.escuela.dto.calificacion;

import com.gina.escuela.dto.datos.DatosInscripcion;
import java.math.BigDecimal;

public record CalificacionResponse(
        Long id,
        DatosInscripcion inscripcion,
        BigDecimal calificaion,
        String fechaRegistro
) {
}
