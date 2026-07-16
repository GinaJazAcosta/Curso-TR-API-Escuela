package com.gina.escuela.services.cursos;

import com.gina.escuela.dto.aula.AulaRequest;
import com.gina.escuela.dto.curso.CursoRequest;
import com.gina.escuela.dto.curso.CursoResponse;
import com.gina.escuela.entities.Curso;
import com.gina.escuela.entities.Maestro;
import com.gina.escuela.exceptions.EntidadRelacionadaException;
import com.gina.escuela.exceptions.RecursoNoEncontradoException;
import com.gina.escuela.mappers.CursoMapper;
import com.gina.escuela.repositories.CursoRepository;
import com.gina.escuela.repositories.GrupoRepository;
import com.gina.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor // @RequiredArgsConstructor
@Transactional
@Slf4j
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;
    private final GrupoRepository grupoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse> listar(){
        return cursoRepository
                .findAll()
                .stream()
                .map(cursoMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CursoResponse obtenerPorId(Long id) {
        return cursoMapper.entidadAResponse(obtenerCursoExcepcion(id));
    }

    @Override
    public CursoResponse registrar(CursoRequest request) {
        log.info("Registrando curso...");
        validarNombreUnico(request);
        Curso curso = cursoMapper.requestAEntidad(request);
        cursoRepository.save(curso);
        log.info("Nuevo curso {} registrado", curso.getNombre());
        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public CursoResponse actualizar(CursoRequest request, Long id) {
        log.info("Actualizando curso con id: {}", id);
        validarNombreUnicoActualizar(request, id);
        Curso curso = obtenerCursoExcepcion(id);
        curso.actualizar(
                request.nombre(),
                request.descripcion(),
                request.creditos()
        );
        cursoRepository.save(curso);
        log.info("Curso con id: {} actualizado", id);
        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando curso con id: {}", id);
        Curso curso = obtenerCursoExcepcion(id);
        if (grupoRepository.existsByCursoId(id))
            throw new EntidadRelacionadaException("No se puede eliminar al curso ya que tiene grupos asignados");

        cursoRepository.delete(curso);
        log.info("Curso con id: {} eliminado", id);
    }

    private Curso obtenerCursoExcepcion(Long id){
        return ServiceUtils.obtenerEntidadOException(cursoRepository, id, Curso.class);
    }

    private void validarNombreUnico(CursoRequest request){
        log.info("Validando nombre de curso único...");
        if (cursoRepository.existsByNombreIgnoreCase(request.nombre()))
            throw  new IllegalArgumentException("Ya existe un curso registrado con el nombre: " + request.nombre());
    }

    private void validarNombreUnicoActualizar(CursoRequest request, Long id){
        log.info("Validando cambio de nombre de curso único...");
        if (cursoRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre(), id))
            throw  new IllegalArgumentException("Ya existe un curso registrado con el nombre: " + request.nombre());
    }
}