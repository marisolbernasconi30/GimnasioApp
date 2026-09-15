package com.example.demo.dto;

import java.util.Map;


public class EstadisticasDTO {


    private Map<String, Double> porcentajePorTipoEntrenamiento; // ej: {"CROSSFIT": 32.5, "YOGA": 18.0, ...}
    private double porcentajeClientesNuevos;
    private double porcentajeClientesBaja;
    private double totalRecaudado;

    //getters y setters 

    public Map<String, Double> getPorcentajeEntrenamiento() {
        return porcentajePorTipoEntrenamiento;
    }
    public void setPorcentajePorTipoEntrenamiento(Map<String,Double> porcentajePorTipoEntrenamiento) {
        this.porcentajePorTipoEntrenamiento = porcentajePorTipoEntrenamiento;
    }
    
    public double getPorcentajeClienteNuevo(){
        return porcentajeClientesNuevos;
    }
    public void setPorcentajeClienteNuevo(double porcentajeClientesNuevos){
        this.porcentajeClientesNuevos = porcentajeClientesNuevos;
    }
    public double getPorcentajeClientesBaja(){
        return porcentajeClientesBaja;
    }
    public void setPorcentajeClientesBaja(double porcentajeClientesBaja){
        this.porcentajeClientesBaja = porcentajeClientesBaja;
    }

    public double getTotalRecaudado(){
        return totalRecaudado;
    }
    public void setTotalRecaudado(double totalRecaudado){
        this.totalRecaudado = totalRecaudado;
    }
    
}
