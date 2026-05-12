package com.clinica.veterinaria.dto;

// Responsable backend: Juan Hakram Huertas Chergui - G1, contratos de entrada y salida DTO.
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroRequest(
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,
    @NotBlank(message = "Los apellidos son obligatorios")
    String apellidos,
    @NotBlank(message = "El DNI es obligatorio")
    String dni,
    @NotBlank(message = "El telefono es obligatorio")
    String telefono,
    String direccion,
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato valido")
    String email,
    @NotBlank(message = "El usuario es obligatorio")
    String username,
    @NotBlank(message = "La contrasena es obligatoria")
    @Size(min = 6, message = "La contrasena debe tener al menos 6 caracteres")
    String password) {
}
