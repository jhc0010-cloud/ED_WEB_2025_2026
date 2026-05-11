package com.clinica.veterinaria.repository;

import com.clinica.veterinaria.entity.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
}

