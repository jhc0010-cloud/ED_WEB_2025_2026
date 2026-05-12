package com.clinica.veterinaria.service;

// Responsable backend: Juan Hakram Huertas Chergui - G1, logica de negocio backend.
import com.clinica.veterinaria.dto.UsuarioRequest;
import com.clinica.veterinaria.entity.Rol;
import com.clinica.veterinaria.entity.Usuario;
import com.clinica.veterinaria.exception.BusinessException;
import com.clinica.veterinaria.exception.ResourceNotFoundException;
import com.clinica.veterinaria.repository.RolRepository;
import com.clinica.veterinaria.repository.UsuarioRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // TODO FUNCION: Contar usuarios registrados para mostrar estadisticas o comprobar si hay datos iniciales.
    public long count() {
        return this.usuarioRepository.count();
    }

    public List<Usuario> findAll() {
        return this.usuarioRepository.findAll();
    }

    public Usuario create(UsuarioRequest request) {
        if (this.usuarioRepository.findByUsername(request.username()).isPresent()) {
            throw new BusinessException("Ya existe un usuario con ese username");
        }
        if (this.usuarioRepository.findByEmail(request.email()).isPresent()) {
            throw new BusinessException("Ya existe un usuario con ese email");
        }
        Usuario usuario = new Usuario();
        this.applyRequest(usuario, request, true);
        return (Usuario)this.usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, UsuarioRequest request) {
        Usuario usuario = (Usuario)this.usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        this.applyRequest(usuario, request, false);
        return (Usuario)this.usuarioRepository.save(usuario);
    }

    public void delete(Long id) {
        if (!this.usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        this.usuarioRepository.deleteById(id);
    }

    private void applyRequest(Usuario usuario, UsuarioRequest request, boolean requirePassword) {
        Rol rol = (Rol)this.rolRepository.findById(request.rolId()).orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));
        usuario.setNombre(request.nombre());
        usuario.setApellidos(request.apellidos());
        usuario.setEmail(request.email());
        usuario.setUsername(request.username());
        if (request.password() != null && !request.password().isBlank()) {
            usuario.setPassword(this.passwordEncoder.encode((CharSequence)request.password()));
        } else if (requirePassword) {
            throw new BusinessException("La contrase\u00f1a es obligatoria");
        }
        usuario.setActivo(true);
        usuario.setRol(rol);
    }
}
