package com.clinica.veterinaria.repository;

// Responsable backend: Rafael Santiago de la Torre Jimenez - G2, persistencia y consultas.
import com.clinica.veterinaria.entity.Pago;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository
extends JpaRepository<Pago, Long> {
    public List<Pago> findByClienteIdOrderByIdDesc(Long var1);

    public List<Pago> findByClienteUsuarioUsernameOrderByIdDesc(String username);

    public Optional<Pago> findByConsultaId(Long consultaId);
}
