package com.gina.escuela.services.inscripciones;

import com.gina.escuela.dto.inscripcion.InscripcionRequest;
import com.gina.escuela.dto.inscripcion.InscripcionResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class InscripcionesServiceImpl implements InscripcionService{
    @Override
    public List<InscripcionResponse> listar() {
        return List.of();
    }

    @Override
    public InscripcionResponse obtenerPorId(Long id) {
        return null;
    }

    @Override
    public InscripcionResponse registrar(InscripcionRequest request) {
        return null;
    }

    @Override
    public InscripcionResponse actualizar(InscripcionRequest request, Long id) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }
}
