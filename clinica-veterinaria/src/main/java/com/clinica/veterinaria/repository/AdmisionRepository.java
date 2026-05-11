package com.clinica.veterinaria.repository;

// Responsable backend: Rafael Santiago de la Torre Jimenez - G2, persistencia y consultas.
import com.clinica.veterinaria.entity.Admision;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmisionRepository
extends JpaRepository<Admision, Long> {
}
