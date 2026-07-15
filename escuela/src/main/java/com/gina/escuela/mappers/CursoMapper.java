package com.gina.escuela.mappers;

import com.gina.escuela.dto.curso.CursoRequest;
import com.gina.escuela.dto.curso.CursoResponse;
import com.gina.escuela.dto.datos.DatosCurso;
import com.gina.escuela.dto.maestro.MaestroRequest;
import com.gina.escuela.dto.maestro.MaestroResponse;
import com.gina.escuela.entities.Curso;
import com.gina.escuela.entities.Maestro;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper implements CommonMapper<CursoRequest, CursoResponse, Curso>{

    @Override
    public Curso requestAEntidad(CursoRequest request){
        if (request==null) return null;
        String descripcion = request.descripcion() != null
                ? request.descripcion().trim() : null;
        return Curso.builder()
                .nombre(request.nombre().trim())
                .descripcion(descripcion)
                .creditos(request.creditos())
                .build();
    }

    @Override
    public CursoResponse entidadAResponse(Curso entidad){
        if (entidad==null) return null;
        String descripcion = entidad.getDescripcion() == null
                ? "Sin descripción" : entidad.getDescripcion();
        return new CursoResponse(
                entidad.getId(),
                entidad.getNombre(),
                descripcion,
                entidad.getCreditos()
        );
    }

    public DatosCurso entidadADatosCurso(Curso entidad){
        if (entidad==null) return null;
        String descripcion = entidad.getDescripcion() == null
                ? "Sin descripción" : entidad.getDescripcion();
        return new DatosCurso(
                entidad.getNombre(),
                descripcion,
                entidad.getCreditos()
        );
    }
}
