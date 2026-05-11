package com.clinica.veterinaria.repository;

// Responsable backend: Rafael Santiago de la Torre Jimenez - G2, persistencia y consultas.
import com.clinica.veterinaria.entity.Mascota;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository
extends JpaRepository<Mascota, Long> {
    public List<Mascota> findByClienteId(Long var1);

    public List<Mascota> findByClienteUsuarioUsername(String username);

    public Optional<Mascota> findByChip(String var1);
}
