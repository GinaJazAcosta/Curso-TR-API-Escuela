package com.gina.escuela.repositories;

import com.gina.escuela.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {
    boolean existsByGrupoId(Long idGrupo);

    @Query(value = """
            SELECT COUNT(*)
            FROM HORARIOS H
            WHERE H.ID_GRUPO = :idGrupo
              AND H.DIA = :dia
              AND :horaInicio < H.HORA_FIN
              AND :horaFin > H.HORA_INICIO
            """, nativeQuery = true)
    Long  existeTraslapeGrupo(
            @Param("idGrupo") Long idGrupo,
            @Param("dia") String dia,
            @Param("horaInicio") String horaInicio,
            @Param("horaFin") String horaFin
    );

    @Query(value = """
            SELECT COUNT(*)
            FROM HORARIOS H
            INNER JOIN GRUPOS G
                ON G.ID_GRUPO = H.ID_GRUPO
            WHERE G.ID_AULA = :idAula
              AND G.PERIODO = :periodo
              AND H.DIA = :dia
              AND :horaInicio < H.HORA_FIN
              AND :horaFin > H.HORA_INICIO
            """, nativeQuery = true)
    Long  existeTraslapeAula(
            @Param("idAula") Long idAula,
            @Param("periodo") String periodo,
            @Param("dia") String dia,
            @Param("horaInicio") String horaInicio,
            @Param("horaFin") String horaFin
    );

    @Query(value = """
            SELECT COUNT(*)
            FROM HORARIOS H
            WHERE H.ID_GRUPO = :idGrupo
              AND H.ID_HORARIO <> :idHorario
              AND H.DIA = :dia
              AND :horaInicio < H.HORA_FIN
              AND :horaFin > H.HORA_INICIO
            """, nativeQuery = true)
    Long  existeTraslapeGrupoActualizar(
            @Param("idGrupo") Long idGrupo,
            @Param("dia") String dia,
            @Param("horaInicio") String horaInicio,
            @Param("horaFin") String horaFin,
            @Param("idHorario") Long idHorario
    );

    @Query(value = """
            SELECT COUNT(*)
            FROM HORARIOS H
            INNER JOIN GRUPOS G
                ON G.ID_GRUPO = H.ID_GRUPO
            WHERE G.ID_AULA = :idAula
              AND G.PERIODO = :periodo
              AND H.ID_HORARIO <> :idHorario
              AND H.DIA = :dia
              AND :horaInicio < H.HORA_FIN
              AND :horaFin > H.HORA_INICIO
            """, nativeQuery = true)
    Long  existeTraslapeAulaActualizar(
            @Param("idAula") Long idAula,
            @Param("periodo") String periodo,
            @Param("dia") String dia,
            @Param("horaInicio") String horaInicio,
            @Param("horaFin") String horaFin,
            @Param("idHorario") Long idHorario
    );
}

