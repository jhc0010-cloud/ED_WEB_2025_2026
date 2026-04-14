package com.clinica.veterinaria.dto;

import java.math.BigDecimal;

public record PagoRequest(BigDecimal importe, String metodoPago, Long clienteId) {
}
