package com.gina.escuela.dto.datos;

import java.math.BigDecimal;

public record DatosCalificaciones(
        String curso,
        String periodo,
        BigDecimal calificacion
) {
}
