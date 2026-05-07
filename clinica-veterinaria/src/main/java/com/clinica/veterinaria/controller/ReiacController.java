package com.clinica.veterinaria.controller;

import com.clinica.veterinaria.service.ReiacService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reiac")
public class ReiacController {

    private final ReiacService reiacService;

    public ReiacController(ReiacService reiacService) {
        this.reiacService = reiacService;
    }

    @GetMapping
    // TODO FUNCION: Obtener y devolver el listado de registros de este recurso desde la capa de servicio.
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(reiacService.findAll());
    }
}

