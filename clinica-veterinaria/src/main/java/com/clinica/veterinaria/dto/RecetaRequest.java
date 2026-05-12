package com.clinica.veterinaria.dto;

// Responsable backend: Juan Hakram Huertas Chergui - G1, contratos de entrada y salida DTO.
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RecetaRequest(
    @NotBlank(message = "El medicamento es obligatorio")
    String medicamento,
    @NotBlank(message = "La dosis es obligatoria")
    String dosis,
    String indicaciones,
    @NotNull(message = "La consulta es obligatoria")
    Long consultaId) {
}
