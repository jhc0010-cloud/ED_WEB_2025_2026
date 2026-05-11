package com.clinica.veterinaria.service;

import com.clinica.veterinaria.repository.PagoRepository;
import org.springframework.stereotype.Service;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public Object findAll() {
        return pagoRepository.findAll();
    }
}

