package com.clinica.veterinaria.repository;

import com.clinica.veterinaria.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

