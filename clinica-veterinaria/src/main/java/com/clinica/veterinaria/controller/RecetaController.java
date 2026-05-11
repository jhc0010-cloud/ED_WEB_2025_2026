package com.clinica.veterinaria.controller;

// Responsable backend: Juan Hakram Huertas Chergui - G1, capa de control y endpoints REST.
import com.clinica.veterinaria.dto.RecetaRequest;
import com.clinica.veterinaria.service.RecetaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/recetas"})
public class RecetaController {
    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(this.recetaService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody RecetaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.recetaService.create(request));
    }
}
