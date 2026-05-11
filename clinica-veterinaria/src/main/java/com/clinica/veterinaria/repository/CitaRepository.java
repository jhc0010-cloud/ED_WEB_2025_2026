package com.clinica.veterinaria.repository;

import com.clinica.veterinaria.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Long> {
}

