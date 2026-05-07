package com.clinica.veterinaria.service;

import com.clinica.veterinaria.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // TODO FUNCION: Obtener todos los clientes para consultar sus datos de contacto y relacionarlos con mascotas.
    public Object findAll() {
        return clienteRepository.findAll();
    }
}

