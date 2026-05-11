package com.clinica.veterinaria.exception;

// Responsable backend: Juan Hakram Huertas Chergui - G1, manejo de errores backend.
public class BusinessException
extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
