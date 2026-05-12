package com.clinica.veterinaria.service;

// Responsable backend: Juan Hakram Huertas Chergui - G1, logica de negocio backend.
import com.clinica.veterinaria.dto.LoginRequest;
import com.clinica.veterinaria.dto.RegistroRequest;
import com.clinica.veterinaria.entity.Cliente;
import com.clinica.veterinaria.entity.Rol;
import com.clinica.veterinaria.entity.Usuario;
import com.clinica.veterinaria.exception.BusinessException;
import com.clinica.veterinaria.repository.ClienteRepository;
import com.clinica.veterinaria.repository.RolRepository;
import com.clinica.veterinaria.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, RolRepository rolRepository, ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginRequest request) {
        return "Solicitud de login recibida para " + request.username();
    }

    @Transactional
    public String register(RegistroRequest request) {
        if (this.usuarioRepository.findByUsername(request.username()).isPresent()) {
            throw new BusinessException("Ya existe un usuario con ese nombre");
        }
        if (this.usuarioRepository.findByEmail(request.email()).isPresent()) {
            throw new BusinessException("Ya existe un usuario con ese email");
        }
        if (this.clienteRepository.findByDni(request.dni()).isPresent()) {
            throw new BusinessException("Ya existe un cliente con ese DNI");
        }
        Rol rolCliente = this.rolRepository.findByNombre("ROLE_CLIENTE").orElseThrow(() -> new BusinessException("No existe el rol ROLE_CLIENTE"));
        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre());
        usuario.setApellidos(request.apellidos());
        usuario.setEmail(request.email());
        usuario.setUsername(request.username());
        usuario.setPassword(this.passwordEncoder.encode((CharSequence)request.password()));
        usuario.setActivo(true);
        usuario.setRol(rolCliente);
        usuario = (Usuario)this.usuarioRepository.save(usuario);
        Cliente cliente = new Cliente();
        cliente.setNombre(request.nombre());
        cliente.setApellidos(request.apellidos());
        cliente.setDni(request.dni());
        cliente.setTelefono(request.telefono());
        cliente.setEmail(request.email());
        cliente.setDireccion(request.direccion());
        cliente.setUsuario(usuario);
        this.clienteRepository.save(cliente);
        return "Usuario registrado correctamente: " + request.username();
    }
}
