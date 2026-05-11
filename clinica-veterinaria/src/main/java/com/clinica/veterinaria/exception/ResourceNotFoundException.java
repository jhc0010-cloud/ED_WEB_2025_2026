package com.clinica.veterinaria.exception;

// Responsable backend: Juan Hakram Huertas Chergui - G1, manejo de errores backend.
public class ResourceNotFoundException
extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
