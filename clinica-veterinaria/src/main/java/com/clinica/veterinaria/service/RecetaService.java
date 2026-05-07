package com.clinica.veterinaria.service;

import com.clinica.veterinaria.repository.RecetaRepository;
import org.springframework.stereotype.Service;

@Service
public class RecetaService {

    private final RecetaRepository recetaRepository;

    public RecetaService(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    // TODO FUNCION: Obtener las recetas emitidas para consultar medicamento, dosis e indicaciones.
    public Object findAll() {
        return recetaRepository.findAll();
    }
}

