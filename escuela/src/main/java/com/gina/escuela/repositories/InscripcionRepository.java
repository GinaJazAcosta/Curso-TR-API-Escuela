package com.gina.escuela.repositories;

import com.gina.escuela.entities.Alumno;
import com.gina.escuela.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    boolean existsByGrupoId(Long idGrupo);
    boolean existsByAlumnoId(Long idAlumno);

    @Query(value = """
            SELECT COUNT(*)
            FROM INSCRIPCIONES
            WHERE ID_ALUMNO = :idAlumno
              AND ID_GRUPO = :idGrupo
            """, nativeQuery = true)
    Long existeInscripcion(
            @Param("idAlumno") Long idAlumno,
            @Param("idGrupo") Long idGrupo);

    @Query(value = """
            SELECT COUNT(*)
            FROM INSCRIPCIONES
            WHERE ID_ALUMNO = :idAlumno
              AND ID_GRUPO = :idGrupo
              AND ID_INSCRIPCION <> :idInscripcion
            """, nativeQuery = true)
    Long existeInscripcionActualizar(
            @Param("idAlumno") Long idAlumno,
            @Param("idGrupo") Long idGrupo,
            @Param("idInscripcion") Long idInscripcion);
}
