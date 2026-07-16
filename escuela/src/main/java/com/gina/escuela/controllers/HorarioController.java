package com.gina.escuela.controllers;

import com.gina.escuela.dto.horario.HorarioRequest;
import com.gina.escuela.dto.horario.HorarioResponse;
import com.gina.escuela.services.horarios.HorarioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/horarios")
public class HorarioController extends CommonController<HorarioRequest, HorarioResponse, HorarioService>{
    public HorarioController(HorarioService service) {
        super(service);
    }
}
