package com.clinica.veterinaria.security;

// Responsable backend: Juan Hakram Huertas Chergui - G1, seguridad y autenticacion.
import com.clinica.veterinaria.repository.UsuarioRepository;
import com.clinica.veterinaria.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService
implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.usuarioRepository.findByUsername(username).map(UserDetailsImpl::new).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}
