package com.gina.escuela.dto.horario;

import com.gina.escuela.dto.datos.DatosGrupo;

public record HorarioResponse(
        Long id,
        DatosGrupo grupo,
        String horario
) {
}
