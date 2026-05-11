package com.clinica.veterinaria.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.clinica.veterinaria.entity.Cliente;
import com.clinica.veterinaria.entity.EstadoPago;
import com.clinica.veterinaria.entity.Pago;
import com.clinica.veterinaria.entity.Usuario;
import com.clinica.veterinaria.exception.BusinessException;
import com.clinica.veterinaria.repository.ClienteRepository;
import com.clinica.veterinaria.repository.ConsultaRepository;
import com.clinica.veterinaria.repository.PagoRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {
    @Mock
    private PagoRepository pagoRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ConsultaRepository consultaRepository;
    @InjectMocks
    private PagoService pagoService;

    @Test
    void pagarRejectsAlreadyPaidPayment() {
        Pago pago = pagoForUser("cliente", EstadoPago.PAGADO);
        when(this.pagoRepository.findById(1L)).thenReturn(Optional.of(pago));

        assertThatThrownBy(() -> this.pagoService.pagar(1L, "cliente", "TARJETA"))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("ya estaba confirmado");

        verify(this.pagoRepository, never()).save(any());
    }

    @Test
    void pagarRejectsPaymentFromAnotherCustomer() {
        Pago pago = pagoForUser("otra-persona", EstadoPago.PENDIENTE);
        when(this.pagoRepository.findById(1L)).thenReturn(Optional.of(pago));

        assertThatThrownBy(() -> this.pagoService.pagar(1L, "cliente", "TARJETA"))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("no pertenece");

        verify(this.pagoRepository, never()).save(any());
    }

    @Test
    void pagarRejectsInvalidPaymentMethod() {
        Pago pago = pagoForUser("cliente", EstadoPago.PENDIENTE);
        when(this.pagoRepository.findById(1L)).thenReturn(Optional.of(pago));

        assertThatThrownBy(() -> this.pagoService.pagar(1L, "cliente", "CHEQUE"))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("Metodo de pago no valido");

        verify(this.pagoRepository, never()).save(any());
    }

    @Test
    void pagarMarksPendingPaymentAsPaid() {
        Pago pago = pagoForUser("cliente", EstadoPago.PENDIENTE);
        when(this.pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        when(this.pagoRepository.save(any(Pago.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pago result = this.pagoService.pagar(1L, "cliente", "bizum");

        assertThat(result.getEstado()).isEqualTo(EstadoPago.PAGADO);
        assertThat(result.getMetodoPago()).isEqualTo("BIZUM");
        assertThat(result.getFechaPago()).isNotNull();
    }

    @Test
    void confirmarPorAuxiliarMarksPendingPaymentAsPaidWithoutCustomerUsername() {
        Pago pago = pagoForUser("cliente", EstadoPago.PENDIENTE);
        when(this.pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        when(this.pagoRepository.save(any(Pago.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pago result = this.pagoService.confirmarPorAuxiliar(1L, "efectivo");

        assertThat(result.getEstado()).isEqualTo(EstadoPago.PAGADO);
        assertThat(result.getMetodoPago()).isEqualTo("EFECTIVO");
        assertThat(result.getFechaPago()).isNotNull();
    }

    private static Pago pagoForUser(String username, EstadoPago estado) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        Pago pago = new Pago();
        pago.setCliente(cliente);
        pago.setEstado(estado);
        return pago;
    }
}
