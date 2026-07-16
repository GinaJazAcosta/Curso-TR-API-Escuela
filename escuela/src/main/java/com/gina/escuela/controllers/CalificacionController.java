package com.gina.escuela.controllers;

import com.gina.escuela.dto.calificacion.CalificacionRequest;
import com.gina.escuela.dto.calificacion.CalificacionResponse;
import com.gina.escuela.services.calificaciones.CalificacionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/califiaciones")
public class CalificacionController extends CommonController<CalificacionRequest, CalificacionResponse, CalificacionService>{
    public CalificacionController(CalificacionService service) {
        super(service);
    }
}
