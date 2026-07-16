package com.gina.escuela.services.horarios;

import com.gina.escuela.dto.horario.HorarioRequest;
import com.gina.escuela.dto.horario.HorarioResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HorarioServiceImpl implements HorarioService{
    @Override
    public List<HorarioResponse> listar() {
        return List.of();
    }

    @Override
    public HorarioResponse obtenerPorId(Long id) {
        return null;
    }

    @Override
    public HorarioResponse registrar(HorarioRequest request) {
        return null;
    }

    @Override
    public HorarioResponse actualizar(HorarioRequest request, Long id) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }
}
