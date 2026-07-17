package com.gina.escuela.mappers;

import com.gina.escuela.dto.datos.DatosAula;
import com.gina.escuela.dto.datos.DatosCurso;
import com.gina.escuela.dto.datos.DatosMaestro;
import com.gina.escuela.dto.grupo.GrupoRequest;
import com.gina.escuela.dto.grupo.GrupoResponse;
import com.gina.escuela.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@RequiredArgsConstructor
@Component
public class GrupoMapper implements CommonMapper<GrupoRequest, GrupoResponse, Grupo>{
    @Override
    public Grupo requestAEntidad(GrupoRequest request) {
        if (request == null) return null;
        return Grupo.builder()
                .periodo(request.periodo())
                .build();
    }

    public Grupo requestAEntidad(GrupoRequest request, Curso curso, Maestro maestro, Aula aula) {
        if (request == null) return null;
        Grupo grupo = requestAEntidad(request);
        grupo.asignarDatos(curso, maestro, aula);
        return grupo;
    }

    @Override
    public GrupoResponse entidadAResponse(Grupo entidad) {
        if (entidad == null) return null;
        return new GrupoResponse(
                entidad.getId(),
                entidadADatosCurso(entidad),
                entidadADatosMaestro(entidad),
                entidadADatosAula(entidad),
                entidadAHorarios(entidad),
                entidad.getPeriodo()
        );
    }

    private DatosCurso entidadADatosCurso(Grupo entidad) {
        if (entidad == null || entidad.getCurso() == null) return null;
        Curso curso = entidad.getCurso();
        return new DatosCurso(
                curso.getNombre(),
                curso.getDescripcion(),
                curso.getCreditos()
        );
    }

    private DatosMaestro entidadADatosMaestro(Grupo entidad) {
        if (entidad == null || entidad.getMaestro() == null) return null;
        Maestro maestro = entidad.getMaestro();
        return new DatosMaestro(
                String.join(" ",
                        maestro.getNombre(),
                        maestro.getApellidoPaterno(),
                        maestro.getApellidoMaterno()),
                maestro.getEmail(),
                maestro.getTelefono()
        );
    }

    private DatosAula entidadADatosAula(Grupo entidad) {
        if (entidad == null || entidad.getAula() == null) return null;
        Aula aula = entidad.getAula();
        return new DatosAula(
                aula.getNombre(),
                aula.getCapacidad()
        );
    }

    private List<String> entidadAHorarios(Grupo entidad) {
        if (entidad == null || entidad.getHorarios() == null || entidad.getHorarios().isEmpty()) {
            return List.of();
        }
        return entidad.getHorarios()
                .stream()
                .map(horario ->
                        "%s %s - %s".formatted(
                                horario.getDiaSemana(),
                                horario.getHoraInicio(),
                                horario.getHoraFin()))
                .toList();
    }

}
