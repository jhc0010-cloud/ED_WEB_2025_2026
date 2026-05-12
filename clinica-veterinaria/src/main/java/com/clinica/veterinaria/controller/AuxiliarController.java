package com.clinica.veterinaria.controller;

// Responsable backend: Juan Hakram Huertas Chergui - G1, capa de control y endpoints REST.
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/auxiliar"})
public class AuxiliarController {
    @GetMapping(value={"/dashboard"})
    public ResponseEntity<String> dashboard() {
        return ResponseEntity.ok("Panel de auxiliar operativo");
    }
}
