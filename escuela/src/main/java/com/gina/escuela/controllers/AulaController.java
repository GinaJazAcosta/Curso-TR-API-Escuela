package com.gina.escuela.controllers;

import com.gina.escuela.dto.aula.AulaRequest;
import com.gina.escuela.dto.aula.AulaResponse;
import com.gina.escuela.services.aulas.AulaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aulas")
public class AulaController extends CommonController<AulaRequest, AulaResponse, AulaService>{

    public AulaController(AulaService service) {
        super(service);
    }
}
