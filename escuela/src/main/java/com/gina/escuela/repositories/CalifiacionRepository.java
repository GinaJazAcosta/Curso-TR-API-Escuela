package com.gina.escuela.repositories;

import com.gina.escuela.entities.Calificacion;
import com.gina.escuela.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalifiacionRepository extends JpaRepository<Calificacion, Long> {
}
