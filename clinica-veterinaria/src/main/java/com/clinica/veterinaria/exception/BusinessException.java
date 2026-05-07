package com.clinica.veterinaria.exception;

public class BusinessException extends RuntimeException {

    // TODO FUNCION: Crear una excepcion para errores de reglas de negocio.

    public BusinessException(String message) {
        super(message);
    }
}

