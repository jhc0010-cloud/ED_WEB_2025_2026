package com.clinica.veterinaria.repository;

import com.clinica.veterinaria.entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}

