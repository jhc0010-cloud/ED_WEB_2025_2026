package com.clinica.veterinaria.controller;

// Responsable backend: Juan Hakram Huertas Chergui - G1, capa de control y endpoints REST.
import com.clinica.veterinaria.dto.UsuarioRequest;
import com.clinica.veterinaria.repository.RolRepository;
import com.clinica.veterinaria.service.UsuarioService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/admin"})
public class AdminController {
    private final UsuarioService usuarioService;
    private final RolRepository rolRepository;

    public AdminController(UsuarioService usuarioService, RolRepository rolRepository) {
        this.usuarioService = usuarioService;
        this.rolRepository = rolRepository;
    }

    @GetMapping(value={"/dashboard"})
    public ResponseEntity<String> dashboard() {
        return ResponseEntity.ok("Panel de administracion operativo");
    }

    @GetMapping(value={"/roles"})
    public ResponseEntity<?> roles() {
        return ResponseEntity.ok(this.rolRepository.findAll());
    }

    @GetMapping(value={"/usuarios"})
    public ResponseEntity<?> usuarios() {
        return ResponseEntity.ok(this.usuarioService.findAll());
    }

    @PostMapping(value={"/usuarios"})
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.usuarioService.create(request));
    }

    @PutMapping(value={"/usuarios/{id}"})
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(this.usuarioService.update(id, request));
    }

    @DeleteMapping(value={"/usuarios/{id}"})
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        this.usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
