package com.clinica.veterinaria.controller;

// Responsable backend: Juan Hakram Huertas Chergui - G1, capa de control y endpoints REST.
import com.clinica.veterinaria.dto.MascotaRequest;
import com.clinica.veterinaria.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/mascotas"})
public class MascotaController {
    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping
    public ResponseEntity<?> listar(@RequestParam(required=false) Long clienteId) {
        if (clienteId != null) {
            return ResponseEntity.ok(this.mascotaService.findByCliente(clienteId));
        }
        return ResponseEntity.ok(this.mascotaService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody MascotaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.mascotaService.create(request));
    }

    @PutMapping(value={"/{id}"})
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody MascotaRequest request) {
        return ResponseEntity.ok(this.mascotaService.update(id, request));
    }

    @PutMapping(value={"/{mascotaId}/veterinario/{veterinarioId}"})
    public ResponseEntity<?> asignarVeterinario(@PathVariable Long mascotaId, @PathVariable Long veterinarioId) {
        return ResponseEntity.ok(this.mascotaService.asignarVeterinario(mascotaId, veterinarioId));
    }

    @DeleteMapping(value={"/{id}"})
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        this.mascotaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
