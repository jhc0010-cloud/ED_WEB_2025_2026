package com.clinica.veterinaria.dto;

// Responsable backend: Juan Hakram Huertas Chergui - G1, contratos de entrada y salida DTO.
import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PagoRequest(
    @NotNull(message = "El importe es obligatorio")
    @Positive(message = "El importe debe ser mayor que cero")
    BigDecimal importe,
    String metodoPago,
    @NotNull(message = "El cliente es obligatorio")
    Long clienteId,
    Long consultaId) {
}
