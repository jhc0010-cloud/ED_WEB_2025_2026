package com.clinica.veterinaria.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.clinica.veterinaria.dto.RecetaRequest;
import com.clinica.veterinaria.entity.Consulta;
import com.clinica.veterinaria.entity.Receta;
import com.clinica.veterinaria.repository.ConsultaRepository;
import com.clinica.veterinaria.repository.RecetaRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecetaServiceTest {
    @Mock
    private RecetaRepository recetaRepository;
    @Mock
    private ConsultaRepository consultaRepository;
    @InjectMocks
    private RecetaService recetaService;

    @Test
    void createAllowsSeveralMedicinesForSameConsultation() {
        Consulta consulta = new Consulta();
        when(this.consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        when(this.recetaRepository.save(any(Receta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Receta antibiotico = this.recetaService.create(new RecetaRequest("Antibiotico", "1 cada 12h", "Durante 7 dias", 1L));
        Receta protector = this.recetaService.create(new RecetaRequest("Protector gastrico", "1 cada 24h", "Antes de comer", 1L));

        assertThat(antibiotico.getConsulta()).isSameAs(consulta);
        assertThat(protector.getConsulta()).isSameAs(consulta);
        assertThat(antibiotico.getMedicamento()).isEqualTo("Antibiotico");
        assertThat(protector.getMedicamento()).isEqualTo("Protector gastrico");
        verify(this.recetaRepository, times(2)).save(any(Receta.class));
    }
}
