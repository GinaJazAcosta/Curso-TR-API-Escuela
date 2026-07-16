package com.gina.escuela.dto.grupo;

import jakarta.validation.constraints.*;

public record GrupoRequest(
        @NotNull(message = "El id del curso es requerido")
        @Positive(message = "El ID del curso debe ser positivo")
        Long idCurso,

        @NotNull(message = "El id del maestro es requerido")
        @Positive(message = "El ID del maestro debe ser positivo")
        Long idMaestro,

        @NotNull(message = "El id del aula es requerido")
        @Positive(message = "El ID del aula debe ser positivo")
        Long idAula,

        @NotBlank(message="El teléfono es requerido")
        @Size(min=1,max=20,message="El periodo es requerido y debe tener entre 1 y 20 caracteres")
        String periodo
) {
}
