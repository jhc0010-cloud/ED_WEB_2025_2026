package com.clinica.veterinaria.controller;

// Responsable backend: Juan Hakram Huertas Chergui - G1, capa de control y endpoints REST.
import com.clinica.veterinaria.dto.PagoRequest;
import com.clinica.veterinaria.service.PagoService;
import jakarta.validation.Valid;
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
@RequestMapping(value={"/api/pagos"})
public class PagoController {
    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public ResponseEntity<?> listar(@RequestParam(required=false) Long clienteId) {
        if (clienteId != null) {
            return ResponseEntity.ok(this.pagoService.findByCliente(clienteId));
        }
        return ResponseEntity.ok(this.pagoService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody PagoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.pagoService.create(request));
    }

    @PutMapping(value={"/{id}/confirmar"})
    public ResponseEntity<?> confirmar(@PathVariable Long id, @RequestParam(required=false) String metodoPago) {
        return ResponseEntity.ok(this.pagoService.confirmarPorAuxiliar(id, metodoPago));
    }
}
