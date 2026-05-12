package com.clinica.veterinaria.repository;

// Responsable backend: Rafael Santiago de la Torre Jimenez - G2, persistencia y consultas.
import com.clinica.veterinaria.entity.Consulta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository
extends JpaRepository<Consulta, Long> {
    public Optional<Consulta> findByCitaId(Long var1);

    public List<Consulta> findByCitaMascotaClienteIdOrderByCitaFechaHoraDesc(Long var1);

    public List<Consulta> findByCitaMascotaClienteUsuarioUsernameOrderByCitaFechaHoraDesc(String username);
}
