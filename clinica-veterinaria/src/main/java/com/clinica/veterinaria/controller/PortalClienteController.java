package com.clinica.veterinaria.controller;

// Responsable backend: Juan Hakram Huertas Chergui - G1, portal privado del cliente.
import com.clinica.veterinaria.dto.CitaRequest;
import com.clinica.veterinaria.service.CitaService;
import com.clinica.veterinaria.service.ConsultaService;
import com.clinica.veterinaria.service.MascotaService;
import com.clinica.veterinaria.service.PagoService;
import com.clinica.veterinaria.service.RecetaService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
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
@RequestMapping(value={"/api/portal"})
public class PortalClienteController {
    private final MascotaService mascotaService;
    private final CitaService citaService;
    private final ConsultaService consultaService;
    private final PagoService pagoService;
    private final RecetaService recetaService;

    public PortalClienteController(MascotaService mascotaService, CitaService citaService, ConsultaService consultaService, PagoService pagoService, RecetaService recetaService) {
        this.mascotaService = mascotaService;
        this.citaService = citaService;
        this.consultaService = consultaService;
        this.pagoService = pagoService;
        this.recetaService = recetaService;
    }

    @GetMapping(value={"/resumen"})
    public ResponseEntity<?> resumen(Principal principal) {
        String username = principal.getName();
        Map<String, Object> resumen = new LinkedHashMap<>();
        resumen.put("mascotas", this.mascotaService.findByClienteUsername(username));
        resumen.put("citas", this.citaService.findByClienteUsername(username));
        resumen.put("consultas", this.consultaService.findByClienteUsername(username));
        resumen.put("pagos", this.pagoService.findByClienteUsername(username));
        resumen.put("recetas", this.recetaService.findByClienteUsername(username));
        return ResponseEntity.ok(resumen);
    }

    @GetMapping(value={"/mascotas"})
    public ResponseEntity<?> mascotas(Principal principal) {
        return ResponseEntity.ok(this.mascotaService.findByClienteUsername(principal.getName()));
    }

    @GetMapping(value={"/citas"})
    public ResponseEntity<?> citas(Principal principal) {
        return ResponseEntity.ok(this.citaService.findByClienteUsername(principal.getName()));
    }

    @GetMapping(value={"/citas/disponibles"})
    public ResponseEntity<?> citasDisponibles(@RequestParam(required=false) Long veterinarioId) {
        return ResponseEntity.ok(this.citaService.findDisponibles(veterinarioId));
    }

    @PostMapping(value={"/citas"})
    public ResponseEntity<?> crearCita(Principal principal, @Valid @RequestBody CitaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.citaService.createForCliente(principal.getName(), request));
    }

    @GetMapping(value={"/consultas"})
    public ResponseEntity<?> consultas(Principal principal) {
        return ResponseEntity.ok(this.consultaService.findByClienteUsername(principal.getName()));
    }

    @GetMapping(value={"/recetas"})
    public ResponseEntity<?> recetas(Principal principal) {
        return ResponseEntity.ok(this.recetaService.findByClienteUsername(principal.getName()));
    }

    @GetMapping(value={"/pagos"})
    public ResponseEntity<?> pagos(Principal principal) {
        return ResponseEntity.ok(this.pagoService.findByClienteUsername(principal.getName()));
    }

    @PutMapping(value={"/pagos/{id}/pagar"})
    public ResponseEntity<?> pagar(Principal principal, @PathVariable Long id, @RequestParam(required=false) String metodoPago) {
        return ResponseEntity.ok(this.pagoService.pagar(id, principal.getName(), metodoPago));
    }
}
