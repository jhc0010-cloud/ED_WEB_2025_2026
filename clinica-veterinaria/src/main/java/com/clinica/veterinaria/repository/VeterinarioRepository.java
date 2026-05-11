package com.clinica.veterinaria.repository;

// Responsable backend: Rafael Santiago de la Torre Jimenez - G2, persistencia y consultas.
import com.clinica.veterinaria.entity.Veterinario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarioRepository
extends JpaRepository<Veterinario, Long> {
    public Optional<Veterinario> findByNumeroColegiado(String var1);

    public Optional<Veterinario> findByUsuarioUsername(String username);
}
