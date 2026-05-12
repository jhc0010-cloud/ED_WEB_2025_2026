package com.clinica.veterinaria.entity;

// Responsable backend: Rafael Santiago de la Torre Jimenez - G2, modelo de datos JPA.
import com.clinica.veterinaria.entity.Cita;
import com.clinica.veterinaria.entity.EstadoConsulta;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="consultas")
@JsonIgnoreProperties(value={"hibernateLazyInitializer", "handler"})
public class Consulta {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="TEXT")
    private String sintomas;
    @Column(columnDefinition="TEXT")
    private String diagnostico;
    @Column(columnDefinition="TEXT")
    private String tratamiento;
    @Enumerated(value=EnumType.STRING)
    @Column(nullable=false, length=30)
    private EstadoConsulta estado = EstadoConsulta.ABIERTA;
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="cita_id", nullable=false, unique=true)
    private Cita cita;

    public Long getId() {
        return this.id;
    }

    public String getSintomas() {
        return this.sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getDiagnostico() {
        return this.diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return this.tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public EstadoConsulta getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoConsulta estado) {
        this.estado = estado;
    }

    public Cita getCita() {
        return this.cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }
}
