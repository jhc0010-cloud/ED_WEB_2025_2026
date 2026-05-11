package com.clinica.veterinaria.dto;

// Responsable backend: Juan Hakram Huertas Chergui - G1, contratos de entrada y salida DTO.
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequest(
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,
    @NotBlank(message = "Los apellidos son obligatorios")
    String apellidos,
    @NotBlank(message = "El DNI es obligatorio")
    String dni,
    @NotBlank(message = "El telefono es obligatorio")
    String telefono,
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato valido")
    String email,
    String direccion,
    Long usuarioId) {
}
