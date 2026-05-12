package com.clinica.veterinaria.service;

// Responsable backend: Juan Hakram Huertas Chergui - G1, logica de negocio backend.
import com.clinica.veterinaria.entity.VerificacionReiac;
import com.clinica.veterinaria.repository.VerificacionReiacRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReiacService {
    private final VerificacionReiacRepository verificacionReiacRepository;

    public ReiacService(VerificacionReiacRepository verificacionReiacRepository) {
        this.verificacionReiacRepository = verificacionReiacRepository;
    }

    public List<VerificacionReiac> findAll() {
        return this.verificacionReiacRepository.findAll();
    }

    public VerificacionReiac verificar(String chip) {
        VerificacionReiac verificacion = new VerificacionReiac();
        verificacion.setCodigoMicrochip(chip);
        verificacion.setFechaConsulta(LocalDateTime.now());
        verificacion.setResultado(chip != null && chip.trim().length() >= 6 ? "VALIDO" : "NO_ENCONTRADO");
        return (VerificacionReiac)this.verificacionReiacRepository.save(verificacion);
    }
}
