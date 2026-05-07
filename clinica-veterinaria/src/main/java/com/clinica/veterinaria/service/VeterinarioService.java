package com.clinica.veterinaria.service;

import com.clinica.veterinaria.repository.VeterinarioRepository;
import org.springframework.stereotype.Service;

@Service
public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioService(VeterinarioRepository veterinarioRepository) {
        this.veterinarioRepository = veterinarioRepository;
    }

    // TODO FUNCION: Obtener todos los veterinarios para consultar colegiado, especialidad y disponibilidad futura.
    public Object findAll() {
        return veterinarioRepository.findAll();
    }
}

