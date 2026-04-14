package com.clinica.veterinaria.dto;

import java.time.LocalDateTime;

public record CitaRequest(LocalDateTime fechaHora, String motivo, Long mascotaId, Long veterinarioId) {
}

