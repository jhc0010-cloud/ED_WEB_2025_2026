package com.clinica.veterinaria.service;

// Responsable backend: Juan Hakram Huertas Chergui - G1, logica de negocio backend.
import com.clinica.veterinaria.dto.PagoRequest;
import com.clinica.veterinaria.entity.Cliente;
import com.clinica.veterinaria.entity.Consulta;
import com.clinica.veterinaria.entity.EstadoPago;
import com.clinica.veterinaria.entity.Pago;
import com.clinica.veterinaria.exception.BusinessException;
import com.clinica.veterinaria.exception.ResourceNotFoundException;
import com.clinica.veterinaria.repository.ClienteRepository;
import com.clinica.veterinaria.repository.ConsultaRepository;
import com.clinica.veterinaria.repository.PagoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class PagoService {
    private static final Set<String> METODOS_PERMITIDOS = Set.of("TARJETA", "BIZUM", "TRANSFERENCIA", "EFECTIVO");
    private final PagoRepository pagoRepository;
    private final ClienteRepository clienteRepository;
    private final ConsultaRepository consultaRepository;

    public PagoService(PagoRepository pagoRepository, ClienteRepository clienteRepository, ConsultaRepository consultaRepository) {
        this.pagoRepository = pagoRepository;
        this.clienteRepository = clienteRepository;
        this.consultaRepository = consultaRepository;
    }

    public List<Pago> findAll() {
        return this.pagoRepository.findAll();
    }

    public List<Pago> findByCliente(Long clienteId) {
        return this.pagoRepository.findByClienteIdOrderByIdDesc(clienteId);
    }

    public List<Pago> findByClienteUsername(String username) {
        return this.pagoRepository.findByClienteUsuarioUsernameOrderByIdDesc(username);
    }

    public Pago create(PagoRequest request) {
        if (request == null || request.importe() == null || request.importe().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El importe debe ser mayor que cero");
        }
        Cliente cliente = (Cliente)this.clienteRepository.findById(request.clienteId()).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        Consulta consulta = null;
        if (request.consultaId() != null) {
            consulta = (Consulta)this.consultaRepository.findById(request.consultaId()).orElseThrow(() -> new ResourceNotFoundException("Consulta no encontrada"));
        }
        Pago pago = new Pago();
        pago.setCliente(cliente);
        pago.setConsulta(consulta);
        pago.setImporte(request.importe());
        pago.setMetodoPago(this.normalizarMetodoPago(request.metodoPago()));
        pago.setEstado(EstadoPago.PAGADO);
        pago.setFechaPago(LocalDateTime.now());
        return (Pago)this.pagoRepository.save(pago);
    }

    public Pago pagar(Long pagoId, String username, String metodoPago) {
        Pago pago = (Pago)this.pagoRepository.findById(pagoId).orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));
        if (pago.getCliente() == null || pago.getCliente().getUsuario() == null || !pago.getCliente().getUsuario().getUsername().equals(username)) {
            throw new BusinessException("El pago no pertenece al cliente autenticado");
        }
        return this.confirmarPago(pago, metodoPago);
    }

    public Pago confirmarPorAuxiliar(Long pagoId, String metodoPago) {
        Pago pago = (Pago)this.pagoRepository.findById(pagoId).orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));
        return this.confirmarPago(pago, metodoPago);
    }

    private Pago confirmarPago(Pago pago, String metodoPago) {
        if (EstadoPago.PAGADO.equals(pago.getEstado())) {
            throw new BusinessException("El pago ya estaba confirmado");
        }
        pago.setMetodoPago(this.normalizarMetodoPago(metodoPago));
        pago.setEstado(EstadoPago.PAGADO);
        pago.setFechaPago(LocalDateTime.now());
        return (Pago)this.pagoRepository.save(pago);
    }

    private String normalizarMetodoPago(String metodoPago) {
        String metodo = metodoPago == null || metodoPago.isBlank() ? "TARJETA" : metodoPago.trim().toUpperCase(Locale.ROOT);
        if (!METODOS_PERMITIDOS.contains(metodo)) {
            throw new BusinessException("Metodo de pago no valido");
        }
        return metodo;
    }
}
