package com.clinica.veterinaria.dto;

// Responsable backend: Juan Hakram Huertas Chergui - G1, contratos de entrada y salida DTO.
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequest(
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,
    @NotBlank(message = "Los apellidos son obligatorios")
    String apellidos,
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato valido")
    String email,
    @NotBlank(message = "El usuario es obligatorio")
    String username,
    String password,
    @NotNull(message = "El rol es obligatorio")
    Long rolId) {
}
