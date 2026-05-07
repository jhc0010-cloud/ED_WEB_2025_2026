package com.clinica.veterinaria.service;

import com.clinica.veterinaria.dto.LoginRequest;
import com.clinica.veterinaria.dto.RegistroRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // TODO FUNCION: Comprobar que el usuario existe, validar la contrasena y devolver el resultado del acceso.
    public String login(LoginRequest request) {
        return "Solicitud de login recibida para " + request.username();
    }

    // TODO FUNCION: Validar los datos del formulario, cifrar la contrasena y guardar el nuevo usuario.
    public String register(RegistroRequest request) {
        return "Usuario registrado en modo plantilla: " + request.username();
    }
}

