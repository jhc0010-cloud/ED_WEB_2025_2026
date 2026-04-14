package com.clinica.veterinaria.service;

import com.clinica.veterinaria.repository.VerificacionReiacRepository;
import org.springframework.stereotype.Service;

@Service
public class ReiacService {

    private final VerificacionReiacRepository verificacionReiacRepository;

    public ReiacService(VerificacionReiacRepository verificacionReiacRepository) {
        this.verificacionReiacRepository = verificacionReiacRepository;
    }

    public Object findAll() {
        return verificacionReiacRepository.findAll();
    }
}

