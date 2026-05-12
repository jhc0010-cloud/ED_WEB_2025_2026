package com.clinica.veterinaria.dto;

// Responsable backend: Juan Hakram Huertas Chergui - G1, contratos de entrada y salida DTO.
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConsultaRequest(
    @NotBlank(message = "Los sintomas son obligatorios")
    String sintomas,
    @NotBlank(message = "El diagnostico es obligatorio")
    String diagnostico,
    @NotBlank(message = "El tratamiento es obligatorio")
    String tratamiento,
    @NotNull(message = "La cita es obligatoria")
    Long citaId) {
}
