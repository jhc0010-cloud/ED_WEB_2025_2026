package com.clinica.veterinaria.service;

// Responsable backend: Juan Hakram Huertas Chergui - G1, logica de negocio backend.
import com.clinica.veterinaria.dto.MascotaRequest;
import com.clinica.veterinaria.entity.Cliente;
import com.clinica.veterinaria.entity.Mascota;
import com.clinica.veterinaria.entity.Veterinario;
import com.clinica.veterinaria.exception.BusinessException;
import com.clinica.veterinaria.exception.ResourceNotFoundException;
import com.clinica.veterinaria.repository.ClienteRepository;
import com.clinica.veterinaria.repository.MascotaRepository;
import com.clinica.veterinaria.repository.VeterinarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MascotaService {
    private final MascotaRepository mascotaRepository;
    private final ClienteRepository clienteRepository;
    private final VeterinarioRepository veterinarioRepository;

    public MascotaService(MascotaRepository mascotaRepository, ClienteRepository clienteRepository, VeterinarioRepository veterinarioRepository) {
        this.mascotaRepository = mascotaRepository;
        this.clienteRepository = clienteRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    public List<Mascota> findAll() {
        return this.mascotaRepository.findAll();
    }

    public List<Mascota> findByCliente(Long clienteId) {
        return this.mascotaRepository.findByClienteId(clienteId);
    }

    public List<Mascota> findByClienteUsername(String username) {
        return this.mascotaRepository.findByClienteUsuarioUsername(username);
    }

    public Mascota findById(Long id) {
        return (Mascota)this.mascotaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada"));
    }

    public Mascota create(MascotaRequest request) {
        if (request.chip() != null && !request.chip().isBlank() && this.mascotaRepository.findByChip(request.chip()).isPresent()) {
            throw new BusinessException("Ya existe una mascota con ese chip");
        }
        Mascota mascota = new Mascota();
        this.applyRequest(mascota, request);
        mascota.setActiva(true);
        mascota.setReiacVerificado(this.simularVerificacionReiac(request.chip()));
        return (Mascota)this.mascotaRepository.save(mascota);
    }

    public Mascota update(Long id, MascotaRequest request) {
        Mascota mascota = this.findById(id);
        this.applyRequest(mascota, request);
        mascota.setReiacVerificado(this.simularVerificacionReiac(request.chip()));
        return (Mascota)this.mascotaRepository.save(mascota);
    }

    public Mascota asignarVeterinario(Long mascotaId, Long veterinarioId) {
        Mascota mascota = this.findById(mascotaId);
        Veterinario veterinario = (Veterinario)this.veterinarioRepository.findById(veterinarioId).orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado"));
        mascota.setVeterinario(veterinario);
        return (Mascota)this.mascotaRepository.save(mascota);
    }

    public void delete(Long id) {
        Mascota mascota = this.findById(id);
        mascota.setActiva(false);
        this.mascotaRepository.save(mascota);
    }

    private void applyRequest(Mascota mascota, MascotaRequest request) {
        Cliente cliente = (Cliente)this.clienteRepository.findById(request.clienteId()).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        mascota.setNombre(request.nombre());
        mascota.setEspecie(request.especie());
        mascota.setRaza(request.raza());
        mascota.setFechaNacimiento(request.fechaNacimiento());
        mascota.setSexo(request.sexo());
        mascota.setChip(request.chip());
        mascota.setCliente(cliente);
        if (request.veterinarioId() != null) {
            Veterinario veterinario = (Veterinario)this.veterinarioRepository.findById(request.veterinarioId()).orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado"));
            mascota.setVeterinario(veterinario);
        }
    }

    private boolean simularVerificacionReiac(String chip) {
        return chip != null && chip.trim().length() >= 6;
    }
}
