package com.example.demo.dto;

import java.time.LocalDate;
import com.example.demo.entity.enums.TipoEntrenamiento;

public class InscripcionDTO {

    private Long clienteId;
    private TipoEntrenamiento tipoEntrenamiento;
    private LocalDate fechaInicio;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
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
}