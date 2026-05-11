package com.clinica.veterinaria.repository;

// Responsable backend: Rafael Santiago de la Torre Jimenez - G2, persistencia y consultas.
import com.clinica.veterinaria.entity.Cita;
import com.clinica.veterinaria.entity.EstadoCita;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository
extends JpaRepository<Cita, Long> {
    public List<Cita> findByVeterinarioIdAndEstadoOrderByFechaHoraAsc(Long var1, EstadoCita var2);

    public List<Cita> findByVeterinarioUsuarioUsernameAndEstadoOrderByFechaHoraAsc(String username, EstadoCita estado);

    public List<Cita> findByMascotaClienteIdOrderByFechaHoraDesc(Long var1);

    public List<Cita> findByMascotaClienteUsuarioUsernameOrderByFechaHoraDesc(String username);

    public boolean existsByVeterinarioIdAndFechaHoraAndEstadoNot(Long veterinarioId, LocalDateTime fechaHora, EstadoCita estado);

    public boolean existsByVeterinarioIdAndFechaHoraAndEstadoAndIdNot(Long veterinarioId, LocalDateTime fechaHora, EstadoCita estado, Long id);

    public List<Cita> findByFechaHoraBetweenAndEstadoNot(LocalDateTime desde, LocalDateTime hasta, EstadoCita estado);
}
