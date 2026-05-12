package com.clinica.veterinaria.dto;

// Responsable backend: Juan Hakram Huertas Chergui - G1, contratos de entrada y salida DTO.
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record MascotaRequest(
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,
    @NotBlank(message = "La especie es obligatoria")
    String especie,
    String raza,
    @PastOrPresent(message = "La fecha de nacimiento no puede ser futura")
    LocalDate fechaNacimiento,
    String sexo,
    @NotBlank(message = "El chip es obligatorio")
    String chip,
    @NotNull(message = "El cliente es obligatorio")
    Long clienteId,
    Long veterinarioId) {
}
