package com.clinica.veterinaria.repository;

import com.clinica.veterinaria.entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
}

