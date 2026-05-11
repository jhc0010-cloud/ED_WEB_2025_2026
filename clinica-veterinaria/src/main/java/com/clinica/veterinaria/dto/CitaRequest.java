package com.clinica.veterinaria.dto;

// Responsable backend: Juan Hakram Huertas Chergui - G1, contratos de entrada y salida DTO.
import java.time.LocalDateTime;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CitaRequest(
    @NotNull(message = "La fecha y hora son obligatorias")
    @Future(message = "La cita debe programarse en una fecha futura")
    LocalDateTime fechaHora,
    @NotBlank(message = "El motivo es obligatorio")
    String motivo,
    @NotNull(message = "La mascota es obligatoria")
    Long mascotaId,
    @NotNull(message = "El veterinario es obligatorio")
    Long veterinarioId) {
}
