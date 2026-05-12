package com.clinica.veterinaria.entity;

// Responsable backend: Rafael Santiago de la Torre Jimenez - G2, modelo de datos JPA.
import com.clinica.veterinaria.entity.EstadoCita;
import com.clinica.veterinaria.entity.Mascota;
import com.clinica.veterinaria.entity.Veterinario;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="citas")
@JsonIgnoreProperties(value={"hibernateLazyInitializer", "handler"})
public class Cita {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private LocalDateTime fechaHora;
    @Column(length=255)
    private String motivo;
    @Enumerated(value=EnumType.STRING)
    @Column(nullable=false, length=30)
    private EstadoCita estado = EstadoCita.PENDIENTE;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="mascota_id", nullable=false)
    private Mascota mascota;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="veterinario_id", nullable=false)
    private Veterinario veterinario;

    public Long getId() {
        return this.id;
    }

    public LocalDateTime getFechaHora() {
        return this.fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return this.motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public EstadoCita getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public Mascota getMascota() {
        return this.mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Veterinario getVeterinario() {
        return this.veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }
}
