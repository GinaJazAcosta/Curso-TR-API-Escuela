package com.gina.escuela.controllers;

import com.gina.escuela.dto.curso.CursoRequest;
import com.gina.escuela.dto.curso.CursoResponse;
import com.gina.escuela.services.cursos.CursoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cursos")
public class CursoController extends CommonController<CursoRequest, CursoResponse, CursoService>{


    public CursoController(CursoService service) {
        super(service);
    }
}
