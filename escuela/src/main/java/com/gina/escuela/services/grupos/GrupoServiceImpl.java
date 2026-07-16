package com.gina.escuela.services.grupos;

import com.gina.escuela.dto.grupo.GrupoRequest;
import com.gina.escuela.dto.grupo.GrupoResponse;
import com.gina.escuela.entities.*;
import com.gina.escuela.exceptions.EntidadRelacionadaException;
import com.gina.escuela.mappers.GrupoMapper;
import com.gina.escuela.repositories.*;
import com.gina.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class GrupoServiceImpl implements GrupoService{

    private final GrupoRepository grupoRepository;
    private final GrupoMapper grupoMapper;

    private final HorarioRepository horarioRepository;
    private final InscripcionRepository inscripcionRepository;
    private final AulaRepository aulaRepository;
    private final MaestroRepository maestroRepository;
    private final CursoRepository cursoRepository;

    @Override
    public List<GrupoResponse> listar() {
        log.info("Listando todos los grupos");
        return grupoRepository.findAll().stream()
                .map(grupoMapper::entidadAResponse).toList();
    }

    @Override
    public GrupoResponse obtenerPorId(Long id) {
        return grupoMapper.entidadAResponse(obtenerGrupo(id));
    }

    @Override
    public GrupoResponse registrar(GrupoRequest request) {
        log.info("Validando datos del grupo");
        Curso curso = ServiceUtils.obtenerEntidadOException(cursoRepository, request.idCurso(), Curso.class);
        Maestro maestro = ServiceUtils.obtenerEntidadOException(maestroRepository, request.idMaestro(), Maestro.class);
        Aula aula = ServiceUtils.obtenerEntidadOException(aulaRepository, request.idAula(), Aula.class);
        if (existeGrupo(request.idCurso(), request.idMaestro(), request.idAula(), request.periodo()))
            throw new IllegalArgumentException("No se puede crear al grupo ya que existe un grupo con los mismos parámetros");

        log.info("Dando de alta grupo");
        Grupo grupo = grupoMapper.requestAEntidad(request, curso, maestro, aula);
        grupoRepository.save(grupo);

        log.info("Grupo creado con éxito");
        //Mapper.entidadAResponse()
        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public GrupoResponse actualizar(GrupoRequest request, Long id) {
        log.info("Validando datos del grupo para actualizar");
        Grupo grupo = obtenerGrupo(id);
        Curso curso = ServiceUtils.obtenerEntidadOException(cursoRepository, request.idCurso(), Curso.class);
        Maestro maestro = ServiceUtils.obtenerEntidadOException(maestroRepository, request.idMaestro(), Maestro.class);
        Aula aula = ServiceUtils.obtenerEntidadOException(aulaRepository, request.idAula(), Aula.class);
        if (existeGrupoExceptoId(request.idCurso(), request.idMaestro(), request.idAula(), request.periodo(), id))
            throw new IllegalArgumentException("No se puede actualizar el grupo ya que existe otro grupo con los mismos parámetros");

        log.info("Actualizando grupo con id: {}", id);
        grupo.actualizar(curso, maestro, aula, request.periodo());
        log.info("Datos académicos generados para el Alumno con id: {}", id);
        grupoRepository.save(grupo);

        log.info("Grupo con id: {} actualizado con éxito", id);
        //Mapper.entidadAResponse()
        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public void eliminar(Long id) {
        Grupo grupo = obtenerGrupo(id);
        log.info("Eliminando grupo con id: {}", id);

        if (horarioRepository.existsByGrupoId(id))
            throw new EntidadRelacionadaException("No se puede eliminar al grupo ya que tiene horarios asignados");
        if (inscripcionRepository.existsByGrupoId(id))
            throw new EntidadRelacionadaException("No se puede eliminar al grupo ya que tiene inscripciones asignados");

        grupoRepository.delete(grupo);
        log.info("Grupo con id: {} eliminado", id);
    }


    private Grupo obtenerGrupo(Long id){
        return ServiceUtils.obtenerEntidadOException(grupoRepository, id, Grupo.class);
    }

    private boolean existeGrupoExceptoId(Long idCurso, Long idMaestro, Long idAula, String periodo, Long idGrupo){
        return grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoAndIdNot(idCurso, idMaestro, idAula, periodo, idGrupo);
    }

    private boolean existeGrupo(Long idCurso, Long idMaestro, Long idAula, String periodo){
        return grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodo(idCurso, idMaestro, idAula, periodo);
    }
}
