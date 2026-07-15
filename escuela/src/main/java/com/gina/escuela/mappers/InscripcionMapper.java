package com.gina.escuela.mappers;

import com.gina.escuela.dto.inscripcion.InscripcionRequest;
import com.gina.escuela.dto.inscripcion.InscripcionResponse;
import com.gina.escuela.entities.Inscripcion;
import org.springframework.stereotype.Component;

@Component
public class InscripcionMapper implements CommonMapper<InscripcionRequest, InscripcionResponse, Inscripcion>{

    @Override
    public Inscripcion requestAEntidad(InscripcionRequest request) {
        return null;
    }

    @Override
    public InscripcionResponse entidadAResponse(Inscripcion entidad) {
        return null;
    }
}
