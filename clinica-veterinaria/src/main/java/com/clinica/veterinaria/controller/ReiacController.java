package com.clinica.veterinaria.controller;

// Responsable backend: Juan Hakram Huertas Chergui - G1, capa de control y endpoints REST.
import com.clinica.veterinaria.service.ReiacService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/reiac"})
public class ReiacController {
    private final ReiacService reiacService;

    public ReiacController(ReiacService reiacService) {
        this.reiacService = reiacService;
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(this.reiacService.findAll());
    }

    @PostMapping(value={"/verificar"})
    public ResponseEntity<?> verificar(@RequestParam String chip) {
        return ResponseEntity.ok(this.reiacService.verificar(chip));
    }
}
