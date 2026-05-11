package com.clinica.veterinaria.entity;

// Responsable backend: Rafael Santiago de la Torre Jimenez - G2, modelo de datos JPA.
import com.clinica.veterinaria.entity.Cliente;
import com.clinica.veterinaria.entity.Veterinario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="mascotas")
@JsonIgnoreProperties(value={"hibernateLazyInitializer", "handler"})
public class Mascota {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, length=100)
    private String nombre;
    @Column(nullable=false, length=50)
    private String especie;
    @Column(length=100)
    private String raza;
    private LocalDate fechaNacimiento;
    @Column(length=20)
    private String sexo;
    @Column(unique=true, length=50)
    private String chip;
    @Column(nullable=false)
    private boolean activa = true;
    @Column(nullable=false)
    private boolean reiacVerificado;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="cliente_id", nullable=false)
    private Cliente cliente;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="veterinario_id")
    private Veterinario veterinario;

    public Long getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return this.especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return this.raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getChip() {
        return this.chip;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }

    public boolean isActiva() {
        return this.activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public boolean isReiacVerificado() {
        return this.reiacVerificado;
    }

    public void setReiacVerificado(boolean reiacVerificado) {
        this.reiacVerificado = reiacVerificado;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veterinario getVeterinario() {
        return this.veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }
}
