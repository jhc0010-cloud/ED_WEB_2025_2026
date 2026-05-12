package com.clinica.veterinaria.controller;

// Responsable backend: Juan Hakram Huertas Chergui - G1, capa de control y endpoints REST.
import com.clinica.veterinaria.dto.CitaRequest;
import com.clinica.veterinaria.entity.EstadoCita;
import com.clinica.veterinaria.service.CitaService;
import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/citas"})
public class CitaController {
    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public ResponseEntity<?> listar(@RequestParam(required=false) Long clienteId) {
        if (clienteId != null) {
            return ResponseEntity.ok(this.citaService.findByCliente(clienteId));
        }
        return ResponseEntity.ok(this.citaService.findAll());
    }

    @GetMapping(value={"/espera/{veterinarioId}"})
    public ResponseEntity<?> listaEspera(@PathVariable Long veterinarioId) {
        return ResponseEntity.ok(this.citaService.listaEspera(veterinarioId));
    }

    @GetMapping(value={"/espera/me"})
    public ResponseEntity<?> miListaEspera(Principal principal) {
        return ResponseEntity.ok(this.citaService.listaEsperaPorVeterinarioUsername(principal.getName()));
    }

    @GetMapping(value={"/disponibles"})
    public ResponseEntity<?> disponibles(@RequestParam(required=false) Long veterinarioId) {
        return ResponseEntity.ok(this.citaService.findDisponibles(veterinarioId));
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CitaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.citaService.create(request));
    }

    @PutMapping(value={"/{id}/estado"})
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam EstadoCita estado) {
        return ResponseEntity.ok(this.citaService.cambiarEstado(id, estado));
    }
}
