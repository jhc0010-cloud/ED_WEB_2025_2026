package com.clinica.veterinaria.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auxiliar")
public class AuxiliarController {

    @GetMapping("/dashboard")
    // TODO FUNCION: Devolver la informacion principal del panel segun el perfil del usuario conectado.
    public ResponseEntity<String> dashboard() {
        return ResponseEntity.ok("Panel de auxiliar operativo");
    }
}

