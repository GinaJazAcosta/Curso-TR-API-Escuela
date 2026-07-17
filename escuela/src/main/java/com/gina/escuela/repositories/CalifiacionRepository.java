package com.gina.escuela.repositories;

import com.gina.escuela.entities.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CalifiacionRepository extends JpaRepository<Calificacion, Long> {
    boolean existsByInscripcionId(Long idInscripcion);

    @Query(value = """
            SELECT COUNT(*)
            FROM CALIFICACIONES
            WHERE ID_INSCRIPCION = :idInscripcion
            """, nativeQuery = true)
    Long existeCalificacion(
            @Param("idInscripcion") Long idInscripcion);

    @Query(value = """
            SELECT COUNT(*)
            FROM CALIFICACIONES
            WHERE ID_INSCRIPCION = :idInscripcion
              AND ID_CALIFICACION <> :idCalificacion
            """, nativeQuery = true)
    Long existeCalificacionActualizar(
            @Param("idInscripcion") Long idInscripcion,
            @Param("idCalificacion") Long idCalificacion);
}
