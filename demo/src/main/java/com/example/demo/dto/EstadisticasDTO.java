package com.example.demo.dto;

import java.util.Map;

public class EstadisticasDTO {

    private Map<String, Double> porcentajePorTipoEntrenamiento; // ej: {"CROSSFIT": 32.5, "YOGA": 18.0, ...}
    private long cantidadClienteNuevo;
    private long cantidadClientesBaja;
    private double totalRecaudado;

    // getters y setters

    public Map<String, Double> getPorcentajeEntrenamiento() {
        return porcentajePorTipoEntrenamiento;
    }

    public void setPorcentajePorTipoEntrenamiento(Map<String, Double> porcentajePorTipoEntrenamiento) {
        this.porcentajePorTipoEntrenamiento = porcentajePorTipoEntrenamiento;
    }

    public long getCantidadClienteNuevo() {
        return cantidadClienteNuevo;
    }

    public void setCantidadClienteNuevo(long cantidadClienteNuevo) {
        this.cantidadClienteNuevo = cantidadClienteNuevo;
    }

    public long getCantidadClientesBaja() {
        return cantidadClientesBaja;
    }

    public void setCantidadClientesBaja(long cantidadClientesBaja) {
        this.cantidadClientesBaja = cantidadClientesBaja;
    }

    public double getTotalRecaudado() {
        return totalRecaudado;
    }

    public void setTotalRecaudado(double totalRecaudado) {
        this.totalRecaudado = totalRecaudado;
    }

}
