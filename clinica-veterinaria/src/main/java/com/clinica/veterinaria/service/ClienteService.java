package com.clinica.veterinaria.service;

// Responsable backend: Juan Hakram Huertas Chergui - G1, logica de negocio backend.
import com.clinica.veterinaria.dto.ClienteRequest;
import com.clinica.veterinaria.entity.Cliente;
import com.clinica.veterinaria.entity.Usuario;
import com.clinica.veterinaria.exception.BusinessException;
import com.clinica.veterinaria.exception.ResourceNotFoundException;
import com.clinica.veterinaria.repository.ClienteRepository;
import com.clinica.veterinaria.repository.UsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public ClienteService(ClienteRepository clienteRepository, UsuarioRepository usuarioRepository) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Cliente> findAll() {
        return this.clienteRepository.findAll();
    }

    public List<Cliente> search(String query) {
        if (query == null || query.isBlank()) {
            return this.findAll();
        }
        String value = query.trim();
        return this.clienteRepository.findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCaseOrDniContainingIgnoreCase(value, value, value);
    }

    public Cliente findById(Long id) {
        return (Cliente)this.clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
    }

    public Cliente create(ClienteRequest request) {
        if (this.clienteRepository.findByDni(request.dni()).isPresent()) {
            throw new BusinessException("Ya existe un cliente con ese DNI");
        }
        Cliente cliente = new Cliente();
        this.applyRequest(cliente, request);
        return (Cliente)this.clienteRepository.save(cliente);
    }

    public Cliente update(Long id, ClienteRequest request) {
        Cliente cliente = this.findById(id);
        this.applyRequest(cliente, request);
        return (Cliente)this.clienteRepository.save(cliente);
    }

    public void delete(Long id) {
        if (!this.clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }
        this.clienteRepository.deleteById(id);
    }

    private void applyRequest(Cliente cliente, ClienteRequest request) {
        cliente.setNombre(request.nombre());
        cliente.setApellidos(request.apellidos());
        cliente.setDni(request.dni());
        cliente.setTelefono(request.telefono());
        cliente.setEmail(request.email());
        cliente.setDireccion(request.direccion());
        if (request.usuarioId() != null) {
            Usuario usuario = (Usuario)this.usuarioRepository.findById(request.usuarioId()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            cliente.setUsuario(usuario);
        }
    }
}
