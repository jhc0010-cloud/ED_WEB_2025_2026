package com.clinica.veterinaria.controller;

import com.clinica.veterinaria.service.MascotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping
    // TODO FUNCION: Obtener y devolver el listado de registros de este recurso desde la capa de servicio.
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(mascotaService.findAll());
    }
}

