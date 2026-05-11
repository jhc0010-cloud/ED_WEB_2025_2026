package com.clinica.veterinaria.repository;

// Responsable backend: Rafael Santiago de la Torre Jimenez - G2, persistencia y consultas.
import com.clinica.veterinaria.entity.VerificacionReiac;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificacionReiacRepository
extends JpaRepository<VerificacionReiac, Long> {
}
