package com.clinica.veterinaria.entity;

// Responsable backend: Rafael Santiago de la Torre Jimenez - G2, modelo de datos JPA.
import com.clinica.veterinaria.entity.Cliente;
import com.clinica.veterinaria.entity.Consulta;
import com.clinica.veterinaria.entity.EstadoPago;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="pagos")
@JsonIgnoreProperties(value={"hibernateLazyInitializer", "handler"})
public class Pago {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, precision=10, scale=2)
    private BigDecimal importe;
    @Column(nullable=false, length=50)
    private String metodoPago;
    @Enumerated(value=EnumType.STRING)
    @Column(nullable=false, length=30)
    private EstadoPago estado = EstadoPago.PENDIENTE;
    private LocalDateTime fechaPago;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="cliente_id", nullable=false)
    private Cliente cliente;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="consulta_id")
    private Consulta consulta;

    public Long getId() {
        return this.id;
    }

    public BigDecimal getImporte() {
        return this.importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getMetodoPago() {
        return this.metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public EstadoPago getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaPago() {
        return this.fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Consulta getConsulta() {
        return this.consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
}
