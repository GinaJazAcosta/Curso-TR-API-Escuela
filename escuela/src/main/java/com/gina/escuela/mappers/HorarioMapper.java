package com.gina.escuela.mappers;


import com.gina.escuela.dto.datos.DatosCurso;
import com.gina.escuela.dto.datos.DatosGrupo;
import com.gina.escuela.dto.horario.HorarioRequest;
import com.gina.escuela.dto.horario.HorarioResponse;
import com.gina.escuela.entities.*;
import com.gina.escuela.enums.DiaSemana;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class HorarioMapper implements CommonMapper<HorarioRequest, HorarioResponse, Horario>{
    @Override
    public Horario requestAEntidad(HorarioRequest request) {
        if (request == null) return null;
        return Horario.builder()
                .horaInicio(request.horaInicio())
                .horaFin(request.horaFin())
                .build();
    }

    public Horario requestAEntidad(HorarioRequest request, Grupo grupo, DiaSemana diaSemana) {
        if (request == null) return null;
        Horario horario = requestAEntidad(request);
        horario.asignarDatos(grupo, diaSemana);
        return horario;
    }








    @Override
    public HorarioResponse entidadAResponse(Horario entidad) {
        if (entidad == null) return null;
        return new HorarioResponse(
                entidad.getId (),
                entidadADatosGrupo(entidad),
                entidadAFormatoHorario(entidad)
        );
    }

    private DatosGrupo entidadADatosGrupo(Horario entidad) {
        if (entidad == null || entidad.getGrupo() == null) return null;
        Grupo grupo = entidad.getGrupo();
        return new DatosGrupo(
                grupo.getCurso().getNombre(),
                grupo.getMaestro().getNombreCompleto(),
                grupo.getAula().getNombre(),
                grupo.getPeriodo()
        );
    }

    private String entidadAFormatoHorario(Horario entidad) {
        if (entidad == null || entidad.getDiaSemana() == null ||
            entidad.getHoraInicio() == null || entidad.getHoraFin() == null) return null;

        return String.join(" ",
                entidad.getDiaSemana().getDescripcion(),
                entidad.getHoraInicio(),
                entidad.getHoraFin()
        );
    }

}
