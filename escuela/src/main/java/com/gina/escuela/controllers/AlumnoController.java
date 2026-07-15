package com.gina.escuela.controllers;

import com.gina.escuela.dto.alumno.AlumnoRequest;
import com.gina.escuela.dto.alumno.AlumnoResponse;
import com.gina.escuela.services.alumnos.AlumnoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/alumnos")
public class AlumnoController extends CommonController<AlumnoRequest, AlumnoResponse, AlumnoService>{
    public AlumnoController(AlumnoService service) {
        super(service);
    }
}
