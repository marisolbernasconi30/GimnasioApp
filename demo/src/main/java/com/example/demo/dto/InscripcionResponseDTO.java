package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.entity.Inscripcion;
import com.example.demo.entity.enums.TipoEntrenamiento;

public class InscripcionResponseDTO {

    private Long id;

    private String nombreCliente;
    private String apellidoCliente;
    private String dni;

    private TipoEntrenamiento tipoEntrenamiento;

    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private LocalDate fechaBaja;

    private boolean activa;
    private boolean pagada;

    public InscripcionResponseDTO(Inscripcion inscripcion, boolean pagada) {

        this.id = inscripcion.getId();

        this.nombreCliente = inscripcion.getCliente().getNombre();
        this.apellidoCliente = inscripcion.getCliente().getApellido();
        this.dni = inscripcion.getCliente().getDni();

        this.tipoEntrenamiento = inscripcion.getTipoEntrenamiento();

        this.fechaInicio = inscripcion.getFechaInicio();
        this.fechaVencimiento = inscripcion.getFechaVencimiento();
        this.fechaBaja = inscripcion.getFechaBaja();

        this.activa = inscripcion.isActiva();
        this.pagada = pagada;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombreCliente() {
        return nombreCliente;
    }  
    public void setNombreCliente (String nombreCliente){
        this.nombreCliente = nombreCliente;
    }
    public String getApellidoCliente() {
        return apellidoCliente;
    }
    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
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
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    public LocalDate getFechaBaja() {
        return fechaBaja;
    }
    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    public boolean isActiva() {
        return activa;
    }
    public void setActiva(boolean activa) {
        this.activa = activa;
    }
    public boolean isPagada() {
        return pagada;
    }
    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }
    
}
