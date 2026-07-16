package com.gina.escuela.repositories;

import com.gina.escuela.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    //SELECT COUNT(*) FROM GRUPOS WHERE ID_MAESTRO = ?
    boolean existsByMaestroId(Long idMaestro);
    boolean existsByAulaId(Long idAula);
    boolean existsByCursoId(Long idCurso);
    boolean existsByCursoIdAndMaestroIdAndAulaIdAndPeriodo(
            Long curso,
            Long maestro,
            Long aula,
            String periodo);
    boolean existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoAndIdNot(
            Long idCurso,
            Long idMaestro,
            Long idAula,
            String periodo,
            Long id);

    @Query(nativeQuery = true, value =
            """
                    SELECT COUNT(*) FROM GRUPOS 
                    WHERE ID_CURSO = :idCurso
                    AND  ID_MAESTRO = :idMaestro
                    AND ID_AULA = :idAula
                    AND PERIODO = :periodo
            """)
    boolean existeGrupo(
            @Param("idCurso") Long idCurso,
            @Param("idMaestro") Long idMaestro,
            @Param("idAula") Long idAula,
            @Param("periodo") String periodo
            );
}
