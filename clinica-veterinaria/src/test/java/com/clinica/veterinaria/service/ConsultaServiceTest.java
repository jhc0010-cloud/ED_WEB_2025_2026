package com.clinica.veterinaria.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.clinica.veterinaria.dto.ConsultaRequest;
import com.clinica.veterinaria.entity.Cita;
import com.clinica.veterinaria.entity.Cliente;
import com.clinica.veterinaria.entity.Consulta;
import com.clinica.veterinaria.entity.EstadoCita;
import com.clinica.veterinaria.entity.EstadoPago;
import com.clinica.veterinaria.entity.Mascota;
import com.clinica.veterinaria.entity.Pago;
import com.clinica.veterinaria.exception.BusinessException;
import com.clinica.veterinaria.repository.CitaRepository;
import com.clinica.veterinaria.repository.ConsultaRepository;
import com.clinica.veterinaria.repository.PagoRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceTest {
    @Mock
    private ConsultaRepository consultaRepository;
    @Mock
    private CitaRepository citaRepository;
    @Mock
    private PagoRepository pagoRepository;
    @InjectMocks
    private ConsultaService consultaService;

    @Test
    void createRejectsAppointmentsThatAreNotConfirmed() {
        Cita cita = new Cita();
        cita.setEstado(EstadoCita.PENDIENTE);
        ConsultaRequest request = new ConsultaRequest("Sintomas", "Diagnostico", "Tratamiento", 1L);
        when(this.consultaRepository.findByCitaId(1L)).thenReturn(Optional.empty());
        when(this.citaRepository.findById(1L)).thenReturn(Optional.of(cita));

        assertThatThrownBy(() -> this.consultaService.create(request))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("cita confirmada");

        verify(this.consultaRepository, never()).save(any());
        verify(this.pagoRepository, never()).save(any());
    }

    @Test
    void createCompletesConfirmedAppointmentAndCreatesPendingPayment() {
        Cliente cliente = new Cliente();
        Mascota mascota = new Mascota();
        mascota.setCliente(cliente);
        Cita cita = new Cita();
        cita.setEstado(EstadoCita.CONFIRMADA);
        cita.setMascota(mascota);
        ConsultaRequest request = new ConsultaRequest("Sintomas", "Diagnostico", "Tratamiento", 1L);
        when(this.consultaRepository.findByCitaId(1L)).thenReturn(Optional.empty());
        when(this.citaRepository.findById(1L)).thenReturn(Optional.of(cita));
        when(this.consultaRepository.save(any(Consulta.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(this.pagoRepository.findByConsultaId(null)).thenReturn(Optional.empty());

        Consulta consulta = this.consultaService.create(request);

        ArgumentCaptor<Pago> pagoCaptor = ArgumentCaptor.forClass(Pago.class);
        verify(this.pagoRepository).save(pagoCaptor.capture());
        assertThat(consulta.getCita().getEstado()).isEqualTo(EstadoCita.COMPLETADA);
        assertThat(pagoCaptor.getValue().getEstado()).isEqualTo(EstadoPago.PENDIENTE);
        assertThat(pagoCaptor.getValue().getCliente()).isSameAs(cliente);
    }
}
