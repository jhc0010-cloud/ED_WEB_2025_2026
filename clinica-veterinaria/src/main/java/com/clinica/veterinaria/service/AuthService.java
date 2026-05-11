package com.clinica.veterinaria.service;

import com.clinica.veterinaria.dto.LoginRequest;
import com.clinica.veterinaria.dto.RegistroRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public String login(LoginRequest request) {
        return "Solicitud de login recibida para " + request.username();
    }

    public String register(RegistroRequest request) {
        return "Usuario registrado en modo plantilla: " + request.username();
    }
}

