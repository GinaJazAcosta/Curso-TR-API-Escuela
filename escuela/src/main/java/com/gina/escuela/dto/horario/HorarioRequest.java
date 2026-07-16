package com.gina.escuela.dto.horario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record HorarioRequest (
        @NotNull(message = "El id del curso es requerido")
        @Positive(message = "El ID del curso debe ser positivo")
        Long idGrupo,

        @NotBlank(message = "El día es requerido")
        @Size(min=5,max=30,message="El dia es requerido y debe tener entre 1 y 15 caracteres")
        String dia,

        @NotBlank(message = "La hora de inicio es requerida")
        @Size(min=1,max=5,message="La hora de inicio es requerida y debe tener entre 1 y 5 caracteres")
        String horaInicio,

        @NotBlank(message = "La hora de fin es requerida")
        @Size(min=1,max=5,message="La hora de fin es requerida y debe tener entre 1 y 5 caracteres")
        String horaFin
){
}
