package com.clinica.veterinaria.service;

// Responsable backend: Juan Hakram Huertas Chergui - G1, logica de negocio backend.
import com.clinica.veterinaria.dto.CitaRequest;
import com.clinica.veterinaria.dto.CitaDisponibleResponse;
import com.clinica.veterinaria.entity.Cliente;
import com.clinica.veterinaria.entity.Cita;
import com.clinica.veterinaria.entity.EstadoCita;
import com.clinica.veterinaria.entity.Mascota;
import com.clinica.veterinaria.entity.Veterinario;
import com.clinica.veterinaria.exception.BusinessException;
import com.clinica.veterinaria.exception.ResourceNotFoundException;
import com.clinica.veterinaria.repository.CitaRepository;
import com.clinica.veterinaria.repository.ClienteRepository;
import com.clinica.veterinaria.repository.MascotaRepository;
import com.clinica.veterinaria.repository.VeterinarioRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

@Service
public class CitaService {
    private static final LocalTime PRIMERA_CITA = LocalTime.of(9, 0);
    private static final LocalTime FIN_JORNADA = LocalTime.of(18, 0);
    private static final int DIAS_DISPONIBLES = 14;
    private final CitaRepository citaRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;
    private final ClienteRepository clienteRepository;

    public CitaService(CitaRepository citaRepository, MascotaRepository mascotaRepository, VeterinarioRepository veterinarioRepository, ClienteRepository clienteRepository) {
        this.citaRepository = citaRepository;
        this.mascotaRepository = mascotaRepository;
        this.veterinarioRepository = veterinarioRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Cita> findAll() {
        return this.citaRepository.findAll();
    }

    public List<Cita> findByCliente(Long clienteId) {
        return this.citaRepository.findByMascotaClienteIdOrderByFechaHoraDesc(clienteId);
    }

    public List<Cita> findByClienteUsername(String username) {
        return this.citaRepository.findByMascotaClienteUsuarioUsernameOrderByFechaHoraDesc(username);
    }

    public List<Cita> listaEspera(Long veterinarioId) {
        return this.citaRepository.findByVeterinarioIdAndEstadoOrderByFechaHoraAsc(veterinarioId, EstadoCita.CONFIRMADA);
    }

    public List<Cita> listaEsperaPorVeterinarioUsername(String username) {
        return this.citaRepository.findByVeterinarioUsuarioUsernameAndEstadoOrderByFechaHoraAsc(username, EstadoCita.CONFIRMADA);
    }

    public Cita findById(Long id) {
        return (Cita)this.citaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));
    }

    public Cita create(CitaRequest request) {
        this.validateRequest(request);
        Mascota mascota = (Mascota)this.mascotaRepository.findById(request.mascotaId()).orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada"));
        Veterinario veterinario = (Veterinario)this.veterinarioRepository.findById(request.veterinarioId()).orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado"));
        return this.createFromEntities(request, mascota, veterinario, EstadoCita.CONFIRMADA);
    }

    public Cita createForCliente(String username, CitaRequest request) {
        this.validateRequest(request);
        Cliente cliente = this.clienteRepository.findByUsuarioUsername(username).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado para el usuario autenticado"));
        Mascota mascota = (Mascota)this.mascotaRepository.findById(request.mascotaId()).orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada"));
        if (mascota.getCliente() == null || !mascota.getCliente().getId().equals(cliente.getId())) {
            throw new BusinessException("La mascota no pertenece al cliente autenticado");
        }
        Veterinario veterinario = (Veterinario)this.veterinarioRepository.findById(request.veterinarioId()).orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado"));
        return this.createFromEntities(request, mascota, veterinario, EstadoCita.PENDIENTE);
    }

    public List<CitaDisponibleResponse> findDisponibles(Long veterinarioId) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime desde = ahora.withSecond(0).withNano(0);
        LocalDateTime hasta = LocalDate.now().plusDays(DIAS_DISPONIBLES).atTime(FIN_JORNADA);
        List<Veterinario> veterinarios = veterinarioId == null
            ? this.veterinarioRepository.findAll()
            : List.of((Veterinario)this.veterinarioRepository.findById(veterinarioId).orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado")));
        Set<String> ocupadas = new HashSet<>();
        this.citaRepository.findByFechaHoraBetweenAndEstadoNot(desde, hasta, EstadoCita.CANCELADA)
            .forEach(cita -> {
                if (cita.getVeterinario() != null && cita.getVeterinario().getId() != null) {
                    ocupadas.add(slotKey(cita.getVeterinario().getId(), cita.getFechaHora()));
                }
            });
        return veterinarios.stream()
            .flatMap(veterinario -> generarHuecos(veterinario, desde, ocupadas).stream())
            .sorted((a, b) -> a.fechaHora().compareTo(b.fechaHora()))
            .toList();
    }

    private void validateRequest(CitaRequest request) {
        if (request == null || request.fechaHora() == null || request.mascotaId() == null || request.veterinarioId() == null) {
            throw new BusinessException("La fecha, mascota y veterinario son obligatorios");
        }
        if (!request.fechaHora().isAfter(LocalDateTime.now())) {
            throw new BusinessException("La cita debe programarse en una fecha futura");
        }
        if (this.citaRepository.existsByVeterinarioIdAndFechaHoraAndEstadoNot(request.veterinarioId(), request.fechaHora(), EstadoCita.CANCELADA)) {
            throw new BusinessException("El veterinario ya tiene una cita programada en esa fecha y hora");
        }
    }

    private List<CitaDisponibleResponse> generarHuecos(Veterinario veterinario, LocalDateTime desde, Set<String> ocupadas) {
        return LocalDate.now().datesUntil(LocalDate.now().plusDays(DIAS_DISPONIBLES + 1L))
            .filter(fecha -> fecha.getDayOfWeek().getValue() <= 5)
            .flatMap(fecha -> IntStream.range(PRIMERA_CITA.getHour(), FIN_JORNADA.getHour())
                .mapToObj(hora -> fecha.atTime(LocalTime.of(hora, 0)))
                .filter(fechaHora -> fechaHora.isAfter(desde))
                .filter(fechaHora -> !ocupadas.contains(slotKey(veterinario.getId(), fechaHora)))
                .map(fechaHora -> new CitaDisponibleResponse(
                    veterinario.getId(),
                    (veterinario.getNombre() + " " + veterinario.getApellidos()).trim(),
                    fechaHora
                )))
            .toList();
    }

    private static String slotKey(Long veterinarioId, LocalDateTime fechaHora) {
        return veterinarioId + "|" + fechaHora;
    }

    private Cita createFromEntities(CitaRequest request, Mascota mascota, Veterinario veterinario, EstadoCita estado) {
        Cita cita = new Cita();
        cita.setFechaHora(request.fechaHora());
        cita.setMotivo(request.motivo());
        cita.setEstado(estado);
        cita.setMascota(mascota);
        cita.setVeterinario(veterinario);
        return (Cita)this.citaRepository.save(cita);
    }

    public Cita cambiarEstado(Long id, EstadoCita estado) {
        Cita cita = this.findById(id);
        if (EstadoCita.CONFIRMADA.equals(estado) && this.citaRepository.existsByVeterinarioIdAndFechaHoraAndEstadoAndIdNot(cita.getVeterinario().getId(), cita.getFechaHora(), EstadoCita.CONFIRMADA, cita.getId())) {
            throw new BusinessException("El veterinario ya tiene una cita confirmada en esa fecha y hora");
        }
        cita.setEstado(estado);
        return (Cita)this.citaRepository.save(cita);
    }
}
