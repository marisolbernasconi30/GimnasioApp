package com.example.demo.dto;

import java.time.LocalDate;
import com.example.demo.entity.Pago;

public class PagoDetalleDTO {

    private String nombreCliente;
    private String apellidoCliente;
    private String dni;
    private String tipoEntrenamiento;
    private LocalDate fechaPago;
    private double monto;
    private String metodoPago;

    public PagoDetalleDTO(Pago pago) {
        this.nombreCliente = pago.getInscripcion().getCliente().getNombre();
        this.apellidoCliente = pago.getInscripcion().getCliente().getApellido();
        this.dni = pago.getInscripcion().getCliente().getDni();
        this.tipoEntrenamiento = pago.getInscripcion().getTipoEntrenamiento().name();
        this.fechaPago = pago.getFechaPago();
        this.monto = pago.getMonto();
        this.metodoPago = pago.getMetodoPago().name();
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public String getDni() {
        return dni;
    }

    public String getTipoEntrenamiento() {
        return tipoEntrenamiento;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public double getMonto() {
        return monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }
}