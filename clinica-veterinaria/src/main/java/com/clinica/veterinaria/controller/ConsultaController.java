package com.clinica.veterinaria.controller;

// Responsable backend: Juan Hakram Huertas Chergui - G1, capa de control y endpoints REST.
import com.clinica.veterinaria.dto.ConsultaRequest;
import com.clinica.veterinaria.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/consultas"})
public class ConsultaController {
    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping
    public ResponseEntity<?> listar(@RequestParam(required=false) Long clienteId) {
        if (clienteId != null) {
            return ResponseEntity.ok(this.consultaService.findByCliente(clienteId));
        }
        return ResponseEntity.ok(this.consultaService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ConsultaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.consultaService.create(request));
    }
}
