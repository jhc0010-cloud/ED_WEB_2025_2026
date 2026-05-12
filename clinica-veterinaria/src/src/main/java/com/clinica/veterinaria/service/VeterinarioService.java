package com.clinica.veterinaria.service;

import com.clinica.veterinaria.repository.VeterinarioRepository;
import org.springframework.stereotype.Service;

@Service
public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioService(VeterinarioRepository veterinarioRepository) {
        this.veterinarioRepository = veterinarioRepository;
    }

    public Object findAll() {
        return veterinarioRepository.findAll();
    }
}

