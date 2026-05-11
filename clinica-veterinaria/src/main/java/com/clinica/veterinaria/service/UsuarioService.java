package com.clinica.veterinaria.service;

import com.clinica.veterinaria.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public long count() {
        return usuarioRepository.count();
    }
}

