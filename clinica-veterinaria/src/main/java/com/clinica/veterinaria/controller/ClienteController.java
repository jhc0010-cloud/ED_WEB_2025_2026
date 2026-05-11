package com.clinica.veterinaria.controller;

// Responsable backend: Juan Hakram Huertas Chergui - G1, capa de control y endpoints REST.
import com.clinica.veterinaria.dto.ClienteRequest;
import com.clinica.veterinaria.service.CitaService;
import com.clinica.veterinaria.service.ClienteService;
import com.clinica.veterinaria.service.ConsultaService;
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
@RequestMapping(value={"/api/clientes"})
public class ClienteController {
    private final ClienteService clienteService;
    private final CitaService citaService;
    private final ConsultaService consultaService;

    public ClienteController(ClienteService clienteService, CitaService citaService, ConsultaService consultaService) {
        this.clienteService = clienteService;
        this.citaService = citaService;
        this.consultaService = consultaService;
    }

    @GetMapping
    public ResponseEntity<?> listar(@RequestParam(required=false) String q) {
        return ResponseEntity.ok(this.clienteService.search(q));
    }

    @GetMapping(value={"/{id}"})
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        return ResponseEntity.ok(this.clienteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.clienteService.create(request));
    }

    @PutMapping(value={"/{id}"})
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(this.clienteService.update(id, request));
    }

    @DeleteMapping(value={"/{id}"})
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        this.clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value={"/{id}/citas"})
    public ResponseEntity<?> citas(@PathVariable Long id) {
        return ResponseEntity.ok(this.citaService.findByCliente(id));
    }

    @GetMapping(value={"/{id}/consultas"})
    public ResponseEntity<?> consultas(@PathVariable Long id) {
        return ResponseEntity.ok(this.consultaService.findByCliente(id));
    }
}
