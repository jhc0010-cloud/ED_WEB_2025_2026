package com.clinica.veterinaria.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.clinica.veterinaria.dto.CitaRequest;
import com.clinica.veterinaria.entity.Cliente;
import com.clinica.veterinaria.entity.Cita;
import com.clinica.veterinaria.entity.EstadoCita;
import com.clinica.veterinaria.entity.Mascota;
import com.clinica.veterinaria.entity.Veterinario;
import com.clinica.veterinaria.exception.BusinessException;
import com.clinica.veterinaria.repository.CitaRepository;
import com.clinica.veterinaria.repository.ClienteRepository;
import com.clinica.veterinaria.repository.MascotaRepository;
import com.clinica.veterinaria.repository.VeterinarioRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class CitaServiceTest {
    @Mock
    private CitaRepository citaRepository;
    @Mock
    private MascotaRepository mascotaRepository;
    @Mock
    private VeterinarioRepository veterinarioRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private CitaService citaService;

    @Test
    void createRejectsPastDate() {
        CitaRequest request = new CitaRequest(LocalDateTime.now().minusDays(1), "Revision", 1L, 2L);

        assertThatThrownBy(() -> this.citaService.create(request))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("fecha futura");

        verifyNoInteractions(this.mascotaRepository, this.veterinarioRepository);
        verify(this.citaRepository, never()).save(any());
    }

    @Test
    void createRejectsVeterinarianScheduleConflict() {
        LocalDateTime fechaHora = LocalDateTime.now().plusDays(1);
        CitaRequest request = new CitaRequest(fechaHora, "Vacunacion", 1L, 2L);
        when(this.citaRepository.existsByVeterinarioIdAndFechaHoraAndEstadoNot(2L, fechaHora, EstadoCita.CANCELADA)).thenReturn(true);

        assertThatThrownBy(() -> this.citaService.create(request))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("ya tiene una cita");

        verifyNoInteractions(this.mascotaRepository, this.veterinarioRepository);
        verify(this.citaRepository, never()).save(any());
    }

    @Test
    void createStoresConfirmedAppointmentWhenDataIsValid() {
        LocalDateTime fechaHora = LocalDateTime.now().plusDays(1);
        CitaRequest request = new CitaRequest(fechaHora, "Vacunacion", 1L, 2L);
        Mascota mascota = new Mascota();
        Veterinario veterinario = new Veterinario();
        when(this.citaRepository.existsByVeterinarioIdAndFechaHoraAndEstadoNot(2L, fechaHora, EstadoCita.CANCELADA)).thenReturn(false);
        when(this.mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));
        when(this.veterinarioRepository.findById(2L)).thenReturn(Optional.of(veterinario));
        when(this.citaRepository.save(any(Cita.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cita cita = this.citaService.create(request);

        assertThat(cita.getFechaHora()).isEqualTo(fechaHora);
        assertThat(cita.getMotivo()).isEqualTo("Vacunacion");
        assertThat(cita.getEstado()).isEqualTo(EstadoCita.CONFIRMADA);
        assertThat(cita.getMascota()).isSameAs(mascota);
        assertThat(cita.getVeterinario()).isSameAs(veterinario);
    }

    @Test
    void createForClienteStoresPendingAppointment() {
        LocalDateTime fechaHora = LocalDateTime.now().plusDays(1);
        CitaRequest request = new CitaRequest(fechaHora, "Revision", 1L, 2L);
        Cliente cliente = new Cliente();
        Mascota mascota = new Mascota();
        Veterinario veterinario = new Veterinario();
        ReflectionTestUtils.setField(cliente, "id", 10L);
        ReflectionTestUtils.setField(mascota, "id", 1L);
        ReflectionTestUtils.setField(veterinario, "id", 2L);
        mascota.setCliente(cliente);
        when(this.clienteRepository.findByUsuarioUsername("cliente")).thenReturn(Optional.of(cliente));
        when(this.mascotaRepository.findById(1L)).thenReturn(Optional.of(mascota));
        when(this.veterinarioRepository.findById(2L)).thenReturn(Optional.of(veterinario));
        when(this.citaRepository.save(any(Cita.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cita cita = this.citaService.createForCliente("cliente", request);

        assertThat(cita.getEstado()).isEqualTo(EstadoCita.PENDIENTE);
    }
}
