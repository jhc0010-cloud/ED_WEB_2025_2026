package com.clinica.veterinaria.service;

// Responsable backend: Juan Hakram Huertas Chergui - G1, logica de negocio backend.
import com.clinica.veterinaria.repository.AdmisionRepository;
import org.springframework.stereotype.Service;

@Service
public class AdmisionService {
    private final AdmisionRepository admisionRepository;

    public AdmisionService(AdmisionRepository admisionRepository) {
        this.admisionRepository = admisionRepository;
    }

    public Object findAll() {
        return this.admisionRepository.findAll();
    }
}
