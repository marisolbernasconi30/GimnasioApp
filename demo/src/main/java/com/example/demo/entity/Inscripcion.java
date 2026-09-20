package com.example.demo.entity;

import java.time.LocalDate;

import com.example.demo.entity.enums.TipoEntrenamiento;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "inscripcion")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @JsonBackReference
   @ManyToOne
   @JoinColumn(name = "cliente_id")
   private Cliente cliente;


    @Enumerated(EnumType.STRING)
    private TipoEntrenamiento tipoEntrenamiento;

    private LocalDate fechaInicio;

    private LocalDate fechaBaja;

    private LocalDate fechaVencimiento;
    
    private boolean activa = true;   

    public Inscripcion() {}

    public Inscripcion(Cliente cliente, TipoEntrenamiento tipoEntrenamiento, LocalDate fechaInicio) {
        this.cliente = cliente;
        this.tipoEntrenamiento = tipoEntrenamiento;
        this.fechaInicio = fechaInicio;
       // this.fechaVencimiento = fechaInicio.plusMonths(1);
        this.fechaVencimiento = null;
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

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}

