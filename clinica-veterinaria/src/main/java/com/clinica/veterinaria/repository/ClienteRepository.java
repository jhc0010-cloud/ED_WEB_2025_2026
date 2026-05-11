package com.clinica.veterinaria.repository;

// Responsable backend: Rafael Santiago de la Torre Jimenez - G2, persistencia y consultas.
import com.clinica.veterinaria.entity.Cliente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository
extends JpaRepository<Cliente, Long> {
    public Optional<Cliente> findByDni(String var1);

    public Optional<Cliente> findByUsuarioUsername(String username);

    public List<Cliente> findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCaseOrDniContainingIgnoreCase(String var1, String var2, String var3);
}
