package com.clinica.veterinaria.config;

import com.clinica.veterinaria.entity.Rol;
import com.clinica.veterinaria.entity.Usuario;
import com.clinica.veterinaria.repository.RolRepository;
import com.clinica.veterinaria.repository.UsuarioRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RolRepository rolRepository,
                           UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (rolRepository.count() == 0) {
            rolRepository.saveAll(List.of(
                    new Rol("ROLE_ADMIN", "Administrador"),
                    new Rol("ROLE_AUXILIAR", "Auxiliar"),
                    new Rol("ROLE_VETERINARIO", "Veterinario"),
                    new Rol("ROLE_CLIENTE", "Cliente")
            ));
        }

        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setNombre("Administrador");
            usuario.setApellidos("Sistema");
            usuario.setUsername("admin");
            usuario.setEmail("admin@clinica.local");
            usuario.setPassword(passwordEncoder.encode("admin123"));
            usuario.setActivo(true);
            usuarioRepository.save(usuario);
        }
    }
}

