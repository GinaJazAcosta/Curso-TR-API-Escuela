package com.gina.escuela.repositories;

import com.gina.escuela.entities.Alumno;
import com.gina.escuela.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    boolean existsByGrupoId(Long idGrupo);
    boolean existsByAlumnoId(Long idAlumno);
}
