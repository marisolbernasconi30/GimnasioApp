package com.example.demo.entity;

import java.time.LocalDate;

import com.example.demo.entity.enums.TipoEntrenamiento;

import jakarta.persistence.*;

@Entity
@Table(name = "inscripcion")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private TipoEntrenamiento tipoEntrenamiento;

    private LocalDate fechaInicio;

    private boolean activa = true;

    public Inscripcion() {}

    public Inscripcion(Cliente cliente, TipoEntrenamiento tipoEntrenamiento, LocalDate fechaInicio) {
        this.cliente = cliente;
        this.tipoEntrenamiento = tipoEntrenamiento;
        this.fechaInicio = fechaInicio;
        this.activa = true;
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoEntrenamiento getTipoEntrenamiento() {
        return tipoEntrenamiento;
    }

    public void setTipoEntrenamiento(TipoEntrenamiento tipoEntrenamiento) {
        this.tipoEntrenamiento = tipoEntrenamiento;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}

