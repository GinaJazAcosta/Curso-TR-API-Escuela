package com.gina.escuela.controllers;

import com.gina.escuela.dto.grupo.GrupoRequest;
import com.gina.escuela.dto.grupo.GrupoResponse;
import com.gina.escuela.services.grupos.GrupoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/grupos")
public class GrupoController extends CommonController<GrupoRequest, GrupoResponse, GrupoService>{
    public GrupoController(GrupoService service) {
        super(service);
    }
}
