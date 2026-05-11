package com.clinica.veterinaria.service;

import com.clinica.veterinaria.repository.ConsultaRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;

    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public Object findAll() {
        return consultaRepository.findAll();
    }
}

