package com.clinica.veterinaria.service;

// Responsable backend: Juan Hakram Huertas Chergui - G1, logica de negocio backend.
import com.clinica.veterinaria.entity.Veterinario;
import com.clinica.veterinaria.exception.ResourceNotFoundException;
import com.clinica.veterinaria.repository.VeterinarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class VeterinarioService {
    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioService(VeterinarioRepository veterinarioRepository) {
        this.veterinarioRepository = veterinarioRepository;
    }

    public List<Veterinario> findAll() {
        return this.veterinarioRepository.findAll();
    }

    public Veterinario findById(Long id) {
        return (Veterinario)this.veterinarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado"));
    }
}
