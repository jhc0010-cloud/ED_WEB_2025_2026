package com.clinica.veterinaria.dto;

// Responsable backend: Juan Hakram Huertas Chergui - G1, contratos de disponibilidad de citas.

import java.time.LocalDateTime;

public record CitaDisponibleResponse(Long veterinarioId, String veterinario, LocalDateTime fechaHora) {
}
