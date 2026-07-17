package com.gina.escuela.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "CALIFICACIONES")
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CALIFICACION")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INSCRIPCION", nullable = false, unique = true)
    private Inscripcion inscripcion;

    @Column(name = "CALIFICACION", nullable = false)
    private BigDecimal calificacion;

    @Column(name = "FECHA_REGISTRO")
    private LocalDate fechaRegistro;

    public void asignarCalificacion( BigDecimal calificacion){
        validarDatos(calificacion);
        this.calificacion = calificacion;
    }
    private void validarDatos(BigDecimal calificacion){
        if (calificacion==null || calificacion.compareTo(BigDecimal.ZERO)<0 || calificacion.compareTo(BigDecimal.TEN) > 0)
            throw new IllegalArgumentException("La calificacion es requerida y debe ser positiva");
    }

    public void actualizar(
            Inscripcion inscripcion){

        if (inscripcion == null)
            throw new IllegalArgumentException("La inscripción es requerida");

        this.inscripcion = inscripcion;
    }
}
