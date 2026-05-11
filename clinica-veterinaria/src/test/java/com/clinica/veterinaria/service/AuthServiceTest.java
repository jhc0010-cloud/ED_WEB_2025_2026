package com.clinica.veterinaria.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.clinica.veterinaria.dto.RegistroRequest;
import com.clinica.veterinaria.entity.Cliente;
import com.clinica.veterinaria.entity.Rol;
import com.clinica.veterinaria.entity.Usuario;
import com.clinica.veterinaria.repository.ClienteRepository;
import com.clinica.veterinaria.repository.RolRepository;
import com.clinica.veterinaria.repository.UsuarioRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RolRepository rolRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthService authService;

    @Test
    void registerCreatesClientRecordLinkedToUser() {
        RegistroRequest request = new RegistroRequest(
            "Lucia",
            "Garcia",
            "12345678Z",
            "600111222",
            "Calle Uno",
            "lucia@example.com",
            "lucia",
            "cliente123"
        );
        when(this.rolRepository.findByNombre("ROLE_CLIENTE")).thenReturn(Optional.of(new Rol("ROLE_CLIENTE", "Cliente")));
        when(this.passwordEncoder.encode("cliente123")).thenReturn("encoded");
        when(this.usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        this.authService.register(request);

        ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);
        verify(this.clienteRepository).save(clienteCaptor.capture());
        Cliente cliente = clienteCaptor.getValue();
        assertThat(cliente.getUsuario().getUsername()).isEqualTo("lucia");
        assertThat(cliente.getDni()).isEqualTo("12345678Z");
        assertThat(cliente.getTelefono()).isEqualTo("600111222");
    }
}
