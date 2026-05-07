package com.clinica.veterinaria.exception;

public class ResourceNotFoundException extends RuntimeException {

    // TODO FUNCION: Crear una excepcion para avisar de que un recurso solicitado no existe.

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

