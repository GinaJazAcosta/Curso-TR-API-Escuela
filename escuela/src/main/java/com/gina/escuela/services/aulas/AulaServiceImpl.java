package com.gina.escuela.services.aulas;

import com.gina.escuela.dto.aula.AulaRequest;
import com.gina.escuela.dto.aula.AulaResponse;
import com.gina.escuela.dto.maestro.MaestroRequest;
import com.gina.escuela.entities.Aula;
import com.gina.escuela.entities.Maestro;
import com.gina.escuela.exceptions.EntidadRelacionadaException;
import com.gina.escuela.exceptions.RecursoNoEncontradoException;
import com.gina.escuela.mappers.AulaMapper;
import com.gina.escuela.repositories.AulaRepository;
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
public class AulaServiceImpl implements AulaService{

    private final AulaRepository aulaRepository;
    private final AulaMapper aulaMapper;

    private final GrupoRepository grupoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AulaResponse> listar(){
        return aulaRepository
                .findAll()
                .stream()
                .map(aulaMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AulaResponse obtenerPorId(Long id) {
        return aulaMapper.entidadAResponse(obtenerAulaExcepcion(id));
    }

    @Override
    public AulaResponse registrar(AulaRequest request) {
        log.info("Registrando aula...");
        validarNombreUnico(request);
        Aula aula = aulaMapper.requestAEntidad(request);
        aulaRepository.save(aula);
        log.info("Nueva aula {} registrada", aula.getNombre());
        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public AulaResponse actualizar(AulaRequest request, Long id) {
        log.info("Actualizando aula con id: {}", id);
        Aula aula = obtenerAulaExcepcion(id);
        validarNombreUnicoActualizar(request, id);
        aula.actualizar(
                request.nombre(),
                request.capacidad()
        );
        aulaRepository.save(aula);
        log.info("Aula con id: {} actualizada", id);
        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando aula con id: {}", id);
        Aula aula = obtenerAulaExcepcion(id);

        if (grupoRepository.existsByAulaId(id))
            throw new EntidadRelacionadaException("No se puede eliminar al aula ya que tiene grupos asignados");
        aulaRepository.delete(aula);
        log.info("Aula con id: {} eliminada", id);
    }

    private Aula obtenerAulaExcepcion(Long id){
        return ServiceUtils.obtenerEntidadOException(aulaRepository, id, Aula.class);
    }

    private void validarNombreUnico(AulaRequest request){
        log.info("Validando nombre de aula único...");
        if (aulaRepository.existsByNombreIgnoreCase(request.nombre()))
            throw  new IllegalArgumentException("Ya existe un aula registrada con el nombre: " + request.nombre());
   }

    private void validarNombreUnicoActualizar(AulaRequest request, Long id){
        log.info("Validando cambio de nombre de aula único...");
        if (aulaRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre(), id))
            throw  new IllegalArgumentException("Ya existe un aula registrada con el nombre: " + request.nombre());
    }
}
