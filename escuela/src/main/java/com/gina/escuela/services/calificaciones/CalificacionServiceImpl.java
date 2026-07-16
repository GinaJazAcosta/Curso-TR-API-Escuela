package com.gina.escuela.services.calificaciones;

import com.gina.escuela.dto.calificacion.CalificacionRequest;
import com.gina.escuela.dto.calificacion.CalificacionResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor // @RequiredArgsConstructor
@Transactional
@Slf4j
public class CalificacionServiceImpl implements CalificacionService{
    @Override
    public List<CalificacionResponse> listar() {
        return List.of();
    }

    @Override
    public CalificacionResponse obtenerPorId(Long id) {
        return null;
    }

    @Override
    public CalificacionResponse registrar(CalificacionRequest request) {
        return null;
    }

    @Override
    public CalificacionResponse actualizar(CalificacionRequest request, Long id) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }
}
