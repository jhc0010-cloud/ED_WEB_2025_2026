package com.clinica.veterinaria.service;

import com.clinica.veterinaria.repository.CitaRepository;
import org.springframework.stereotype.Service;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    // TODO FUNCION: Listar todas las citas con su fecha, motivo y estado para mostrarlas en la agenda.
    public Object findAll() {
        return citaRepository.findAll();
    }
}

