package com.clinica.veterinaria.dto;

public record UsuarioRequest(String nombre, String apellidos, String email, String username, String password, Long rolId) {
}

