package com.gina.escuela.controllers;

import com.gina.escuela.dto.inscripcion.InscripcionRequest;
import com.gina.escuela.dto.inscripcion.InscripcionResponse;
import com.gina.escuela.services.inscripciones.InscripcionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/incripciones")
public class InscripcionController extends CommonController<InscripcionRequest, InscripcionResponse, InscripcionService>{
    public InscripcionController(InscripcionService service) {
        super(service);
    }
}
