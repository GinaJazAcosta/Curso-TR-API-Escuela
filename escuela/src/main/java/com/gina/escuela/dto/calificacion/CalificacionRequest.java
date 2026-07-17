package com.gina.escuela.dto.calificacion;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CalificacionRequest(
        /*
        @NotBlank(message="El nombre es requerido")
        @Size(min=5,max=100,message="El nombre es requerido y debe tener entre 5 y 100 caracteres")
        String nombre,

        @Size(min=5,max=200,message="La descripción es requerida y debe tener entre 5 y 200 caracteres")
        String descripcion,
*/
        @NotNull(message = "La inscripción es requerida")
        @Positive(message = "La inscripción debe ser positiva")
        Long idInscripcion,

        @NotNull(message = "La calificación es requerida")
        @DecimalMin(value = "0.0", inclusive = true, message = "Los créditos mínimos son 0.0")
        @DecimalMax(value = "10.0", inclusive = true, message = "Los créditos máximos son 10.0")
        BigDecimal calificacion
) {
}
