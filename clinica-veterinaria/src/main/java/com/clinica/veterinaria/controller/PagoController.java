package com.clinica.veterinaria.controller;

import com.clinica.veterinaria.service.PagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    // TODO FUNCION: Obtener y devolver el listado de registros de este recurso desde la capa de servicio.
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(pagoService.findAll());
    }
}

