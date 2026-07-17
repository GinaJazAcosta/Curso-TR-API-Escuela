package com.gina.escuela.services.horarios;

import com.gina.escuela.dto.horario.HorarioRequest;
import com.gina.escuela.dto.horario.HorarioResponse;
import com.gina.escuela.entities.*;
import com.gina.escuela.enums.DiaSemana;
import com.gina.escuela.exceptions.EntidadRelacionadaException;
import com.gina.escuela.mappers.HorarioMapper;
import com.gina.escuela.repositories.GrupoRepository;
import com.gina.escuela.repositories.HorarioRepository;
import com.gina.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HorarioServiceImpl implements HorarioService{

    private final HorarioRepository horarioRepository;
    private final HorarioMapper horarioMapper;

    private final GrupoRepository grupoRepository;

    @Override
    public List<HorarioResponse> listar() {
        log.info("Listando todos los Horarios");
        return horarioRepository.findAll().stream()
                .map(horarioMapper::entidadAResponse).toList();
    }

    @Override
    public HorarioResponse obtenerPorId(Long id) {
        return horarioMapper.entidadAResponse(obtenerHorario(id));
    }

    @Override
    public HorarioResponse registrar(HorarioRequest request) {
        log.info("Validando datos del horario");

        Grupo grupo = ServiceUtils.obtenerEntidadOException(grupoRepository, request.idGrupo(), Grupo.class);

        DiaSemana diaSemana = DiaSemana.obtenerDiaSemanaPorDescripcion(request.dia());

        validarTraslapeGrupo(grupo, diaSemana, request);

        validarTraslapeAula(grupo, diaSemana, request);

        Horario horario = horarioMapper.requestAEntidad(request, grupo, diaSemana);
        horario.validarDatos(grupo, diaSemana, request.horaInicio(), request.horaFin());
        horarioRepository.save(horario);

        log.info("Horario registrado correctamente");

        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public HorarioResponse actualizar(HorarioRequest request, Long id) {
        Horario horario = obtenerHorario(id);

        Grupo grupo = ServiceUtils.obtenerEntidadOException(
                grupoRepository,
                request.idGrupo(),
                Grupo.class);
        DiaSemana diaSemana = DiaSemana.obtenerDiaSemanaPorDescripcion(request.dia());

        validarTraslapeGrupoActualizar(grupo, diaSemana, request, id);

        validarTraslapeAulaActualizar(grupo, diaSemana, request, id);

        horario.actualizar(
                grupo,
                diaSemana,
                request.horaInicio(),
                request.horaFin());

        horarioRepository.save(horario);

        log.info("Horario actualizado correctamente");

        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando horario con id: {}", id);
        Horario horario = obtenerHorario(id);

        horarioRepository.delete(horario);
        log.info("Horario con id: {} eliminado", id);
    }

    private Horario obtenerHorario(Long id){
        return ServiceUtils.obtenerEntidadOException(horarioRepository, id, Horario.class);
    }

    private void validarTraslapeGrupo(Grupo grupo, DiaSemana dia, HorarioRequest request) {

        if (horarioRepository.existeTraslapeGrupo(grupo.getId(), dia.name(), request.horaInicio(), request.horaFin())> 0) {
            throw new IllegalArgumentException("Ya existe un horario traslapado para el grupo.");
        }
    }

    private void validarTraslapeAula(Grupo grupo, DiaSemana dia, HorarioRequest request) {

        if (horarioRepository.existeTraslapeAula(grupo.getAula().getId(), grupo.getPeriodo(), dia.name(), request.horaInicio(), request.horaFin())> 0) {

            throw new IllegalArgumentException("Ya existe un horario traslapado para el aula.");
        }
    }

    private void validarTraslapeGrupoActualizar(Grupo grupo, DiaSemana dia, HorarioRequest request, Long idHorario) {

        if (horarioRepository.existeTraslapeGrupoActualizar(grupo.getId(), dia.name(), request.horaInicio(), request.horaFin(), idHorario) > 0) {
            throw new IllegalArgumentException("Ya existe un horario traslapado para el grupo.");
        }
    }

    private void validarTraslapeAulaActualizar(Grupo grupo, DiaSemana dia, HorarioRequest request, Long idHorario) {

        if (horarioRepository.existeTraslapeAulaActualizar(grupo.getAula().getId(), grupo.getPeriodo(), dia.name(), request.horaInicio(), request.horaFin(), idHorario)> 0) {
            throw new IllegalArgumentException("Ya existe un horario traslapado para el aula.");
        }
    }

}
