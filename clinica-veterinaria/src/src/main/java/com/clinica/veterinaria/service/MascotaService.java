package com.clinica.veterinaria.service;

import com.clinica.veterinaria.repository.MascotaRepository;
import org.springframework.stereotype.Service;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    public Object findAll() {
        return mascotaRepository.findAll();
    }
}

