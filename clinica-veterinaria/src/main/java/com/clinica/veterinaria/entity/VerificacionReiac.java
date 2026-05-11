package com.clinica.veterinaria.entity;

// Responsable backend: Rafael Santiago de la Torre Jimenez - G2, modelo de datos JPA.
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="verificaciones_reiac")
public class VerificacionReiac {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String codigoMicrochip;
    private String resultado;
    private LocalDateTime fechaConsulta;

    public Long getId() {
        return this.id;
    }

    public String getCodigoMicrochip() {
        return this.codigoMicrochip;
    }

    public void setCodigoMicrochip(String codigoMicrochip) {
        this.codigoMicrochip = codigoMicrochip;
    }

    public String getResultado() {
        return this.resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public LocalDateTime getFechaConsulta() {
        return this.fechaConsulta;
    }

    public void setFechaConsulta(LocalDateTime fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }
}
