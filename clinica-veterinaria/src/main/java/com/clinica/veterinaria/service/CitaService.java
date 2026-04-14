package com.clinica.veterinaria.service;

import com.clinica.veterinaria.repository.CitaRepository;
import org.springframework.stereotype.Service;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public Object findAll() {
        return citaRepository.findAll();
    }
}

