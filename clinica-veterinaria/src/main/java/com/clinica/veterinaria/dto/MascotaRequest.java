package com.clinica.veterinaria.dto;

import java.time.LocalDate;

public record MascotaRequest(String nombre, String especie, String raza, LocalDate fechaNacimiento, Long clienteId) {
}

