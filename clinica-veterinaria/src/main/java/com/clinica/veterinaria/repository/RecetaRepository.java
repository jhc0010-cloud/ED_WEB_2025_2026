package com.clinica.veterinaria.repository;

// Responsable backend: Rafael Santiago de la Torre Jimenez - G2, persistencia y consultas.
import com.clinica.veterinaria.entity.Receta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaRepository
extends JpaRepository<Receta, Long> {
    public List<Receta> findByConsultaCitaMascotaClienteUsuarioUsernameOrderByConsultaCitaFechaHoraDesc(String username);
}
