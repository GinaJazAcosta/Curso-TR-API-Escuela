package com.gina.escuela.mappers;

import com.gina.escuela.dto.calificacion.CalificacionResponse;
import com.gina.escuela.entities.Calificacion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.CacheRequest;

@RequiredArgsConstructor
@Component
public class CalificacionMapper implements CommonMapper<CacheRequest, CalificacionResponse, Calificacion>{
    @Override
    public Calificacion requestAEntidad(CacheRequest request) {
        return null;
    }

    @Override
    public CalificacionResponse entidadAResponse(Calificacion entidad) {
        return null;
    }
}
