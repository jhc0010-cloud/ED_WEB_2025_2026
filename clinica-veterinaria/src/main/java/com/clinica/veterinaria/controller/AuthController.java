package com.clinica.veterinaria.controller;

// Responsable backend: Juan Hakram Huertas Chergui - G1, capa de control y endpoints REST.
import com.clinica.veterinaria.dto.LoginRequest;
import com.clinica.veterinaria.dto.RegistroRequest;
import com.clinica.veterinaria.entity.Cliente;
import com.clinica.veterinaria.entity.Usuario;
import com.clinica.veterinaria.entity.Veterinario;
import com.clinica.veterinaria.repository.ClienteRepository;
import com.clinica.veterinaria.repository.UsuarioRepository;
import com.clinica.veterinaria.repository.VeterinarioRepository;
import com.clinica.veterinaria.service.AuthService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/auth"})
public class AuthController {
    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final VeterinarioRepository veterinarioRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, UsuarioRepository usuarioRepository, ClienteRepository clienteRepository, VeterinarioRepository veterinarioRepository) {
        this.authService = authService;
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    @PostMapping(value={"/login"})
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication auth = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
            SecurityContextHolder.getContext().setAuthentication(auth);
            return ResponseEntity.ok(("Login correcto. Bienvenido, " + loginRequest.username()));
        }
        catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contrase\u00f1a incorrectos");
        }
    }

    @PostMapping(value={"/register"})
    public ResponseEntity<String> register(@Valid @RequestBody RegistroRequest request) {
        return ResponseEntity.ok(this.authService.register(request));
    }

    @GetMapping(value={"/me"})
    public ResponseEntity<?> me(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "No autenticado"));
        }
        Usuario usuario = this.usuarioRepository.findByUsername(principal.getName()).orElseThrow();
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("id", usuario.getId());
        payload.put("username", usuario.getUsername());
        payload.put("nombre", usuario.getNombre());
        payload.put("apellidos", usuario.getApellidos());
        payload.put("email", usuario.getEmail());
        payload.put("rol", usuario.getRol() != null ? usuario.getRol().getNombre() : null);
        this.clienteRepository.findByUsuarioUsername(usuario.getUsername()).map(Cliente::getId).ifPresent(id -> payload.put("clienteId", id));
        this.veterinarioRepository.findByUsuarioUsername(usuario.getUsername()).map(Veterinario::getId).ifPresent(id -> payload.put("veterinarioId", id));
        return ResponseEntity.ok(payload);
    }
}
