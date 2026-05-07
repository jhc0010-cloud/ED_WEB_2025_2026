package com.clinica.veterinaria.controller;

import com.clinica.veterinaria.dto.LoginRequest;
import com.clinica.veterinaria.dto.RegistroRequest;
import com.clinica.veterinaria.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    // TODO FUNCION: Validar las credenciales recibidas y devolver el resultado del inicio de sesion.
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    // TODO FUNCION: Registrar un usuario nuevo validando sus datos antes de guardarlo.
    public ResponseEntity<String> register(@RequestBody RegistroRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}

