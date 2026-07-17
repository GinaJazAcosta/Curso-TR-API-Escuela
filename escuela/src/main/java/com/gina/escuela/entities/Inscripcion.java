package com.gina.escuela.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "INSCRIPCIONES",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "INSCRIPCION_ALU_GRU_UK",
                        columnNames = { "ID_ALUMNO", "ID_GRUPO" })
        }
)
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INSCRIPCION")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ALUMNO", nullable = false)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO", nullable = false)
    private Grupo grupo;

    @Builder.Default
    @Column(name = "FECHA_INSCRIPCION")
    private LocalDate fechaInscripcion = LocalDate.now();

    @OneToOne(mappedBy = "inscripcion")
    private Calificacion calificacion;

    private void validarDatos(Alumno alumno, Grupo grupo, LocalDate fechaInscripcion){
        if (alumno == null)
            throw new IllegalArgumentException("El alumno es requerido");
        if (grupo == null)
            throw new IllegalArgumentException("El grupo es requerido");
        if (fechaInscripcion == null)
            throw new IllegalArgumentException("La fecha de inscripcion es requerida");
    }

    public void asignarDatos(Alumno alumno, Grupo grupo, LocalDate fechaInscripcion, Calificacion calificacion){
        validarDatos(alumno, grupo, fechaInscripcion);
        this.alumno = alumno;
        this.grupo = grupo;
        this.fechaInscripcion = fechaInscripcion;
        this.calificacion = calificacion;
    }
}
