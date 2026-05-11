package com.clinica.veterinaria.service;

// Responsable backend: Juan Hakram Huertas Chergui - G1, logica de negocio backend.
import com.clinica.veterinaria.dto.ConsultaRequest;
import com.clinica.veterinaria.entity.Cita;
import com.clinica.veterinaria.entity.Consulta;
import com.clinica.veterinaria.entity.EstadoCita;
import com.clinica.veterinaria.entity.EstadoConsulta;
import com.clinica.veterinaria.entity.EstadoPago;
import com.clinica.veterinaria.entity.Pago;
import com.clinica.veterinaria.exception.BusinessException;
import com.clinica.veterinaria.exception.ResourceNotFoundException;
import com.clinica.veterinaria.repository.CitaRepository;
import com.clinica.veterinaria.repository.ConsultaRepository;
import com.clinica.veterinaria.repository.PagoRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {
    private final ConsultaRepository consultaRepository;
    private final CitaRepository citaRepository;
    private final PagoRepository pagoRepository;

    public ConsultaService(ConsultaRepository consultaRepository, CitaRepository citaRepository, PagoRepository pagoRepository) {
        this.consultaRepository = consultaRepository;
        this.citaRepository = citaRepository;
        this.pagoRepository = pagoRepository;
    }

    public List<Consulta> findAll() {
        return this.consultaRepository.findAll();
    }

    public List<Consulta> findByCliente(Long clienteId) {
        return this.consultaRepository.findByCitaMascotaClienteIdOrderByCitaFechaHoraDesc(clienteId);
    }

    public List<Consulta> findByClienteUsername(String username) {
        return this.consultaRepository.findByCitaMascotaClienteUsuarioUsernameOrderByCitaFechaHoraDesc(username);
    }

    public Consulta create(ConsultaRequest request) {
        if (this.consultaRepository.findByCitaId(request.citaId()).isPresent()) {
            throw new BusinessException("La cita ya tiene una consulta registrada");
        }
        Cita cita = (Cita)this.citaRepository.findById(request.citaId()).orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));
        if (!EstadoCita.CONFIRMADA.equals(cita.getEstado())) {
            throw new BusinessException("Solo se puede registrar consulta sobre una cita confirmada");
        }
        Consulta consulta = new Consulta();
        consulta.setCita(cita);
        consulta.setSintomas(request.sintomas());
        consulta.setDiagnostico(request.diagnostico());
        consulta.setTratamiento(request.tratamiento());
        consulta.setEstado(EstadoConsulta.CERRADA);
        cita.setEstado(EstadoCita.COMPLETADA);
        this.citaRepository.save(cita);
        consulta = (Consulta)this.consultaRepository.save(consulta);
        if (cita.getMascota() != null && cita.getMascota().getCliente() != null && this.pagoRepository.findByConsultaId(consulta.getId()).isEmpty()) {
            Pago pago = new Pago();
            pago.setCliente(cita.getMascota().getCliente());
            pago.setConsulta(consulta);
            pago.setImporte(BigDecimal.valueOf(45.0));
            pago.setMetodoPago("PENDIENTE");
            pago.setEstado(EstadoPago.PENDIENTE);
            this.pagoRepository.save(pago);
        }
        return consulta;
    }
}
