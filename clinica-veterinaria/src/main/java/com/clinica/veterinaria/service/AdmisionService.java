package com.clinica.veterinaria.service;

import com.clinica.veterinaria.repository.AdmisionRepository;
import org.springframework.stereotype.Service;

@Service
public class AdmisionService {

    private final AdmisionRepository admisionRepository;

    public AdmisionService(AdmisionRepository admisionRepository) {
        this.admisionRepository = admisionRepository;
    }

    public Object findAll() {
        return admisionRepository.findAll();
    }
}

