package com.clinica.veterinaria.service;

// Responsable backend: Juan Hakram Huertas Chergui - G1, logica de negocio backend.
import com.clinica.veterinaria.dto.RecetaRequest;
import com.clinica.veterinaria.entity.Consulta;
import com.clinica.veterinaria.entity.Receta;
import com.clinica.veterinaria.exception.ResourceNotFoundException;
import com.clinica.veterinaria.repository.ConsultaRepository;
import com.clinica.veterinaria.repository.RecetaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RecetaService {
    private final RecetaRepository recetaRepository;
    private final ConsultaRepository consultaRepository;

    public RecetaService(RecetaRepository recetaRepository, ConsultaRepository consultaRepository) {
        this.recetaRepository = recetaRepository;
        this.consultaRepository = consultaRepository;
    }

    public List<Receta> findAll() {
        return this.recetaRepository.findAll();
    }

    public List<Receta> findByClienteUsername(String username) {
        return this.recetaRepository.findByConsultaCitaMascotaClienteUsuarioUsernameOrderByConsultaCitaFechaHoraDesc(username);
    }

    public Receta create(RecetaRequest request) {
        Consulta consulta = (Consulta)this.consultaRepository.findById(request.consultaId()).orElseThrow(() -> new ResourceNotFoundException("Consulta no encontrada"));
        Receta receta = new Receta();
        receta.setConsulta(consulta);
        receta.setMedicamento(request.medicamento());
        receta.setDosis(request.dosis());
        receta.setIndicaciones(request.indicaciones());
        return (Receta)this.recetaRepository.save(receta);
    }
}
