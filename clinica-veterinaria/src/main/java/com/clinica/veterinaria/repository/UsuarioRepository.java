package com.clinica.veterinaria.repository;

// Responsable backend: Rafael Santiago de la Torre Jimenez - G2, persistencia y consultas.
import com.clinica.veterinaria.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository
extends JpaRepository<Usuario, Long> {
    public Optional<Usuario> findByUsername(String var1);

    public Optional<Usuario> findByEmail(String var1);
}
