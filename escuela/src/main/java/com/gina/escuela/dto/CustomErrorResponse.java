package com.gina.escuela.dto;

public record CustomErrorResponse(
        int codigo,
        String mensaje
) {
}
