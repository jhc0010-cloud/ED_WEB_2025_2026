package com.clinica.veterinaria.service;

import com.clinica.veterinaria.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // TODO FUNCION: Contar usuarios registrados para mostrar estadisticas o comprobar si hay datos iniciales.
    public long count() {
        return usuarioRepository.count();
    }
}

