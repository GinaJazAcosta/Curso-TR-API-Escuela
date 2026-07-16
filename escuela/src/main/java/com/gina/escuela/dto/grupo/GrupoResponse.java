package com.gina.escuela.dto.grupo;

import com.gina.escuela.dto.datos.DatosCurso;
import com.gina.escuela.dto.datos.DatosAula;
import com.gina.escuela.dto.datos.DatosMaestro;

import java.util.List;

public record GrupoResponse(
        Long id,
        DatosCurso curso,
        DatosMaestro maestro,
        DatosAula aula,
        List<String> horarios,
        String periodo
) {
}
