package com.example.demo.entity;

//import java.util.List;

//import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes_gimnasio")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String dni;
    private int edad;
    private String celular;
    private String domicilio;
    private String lesion;

    private boolean activo = true;

  //  @JsonManagedReference
   // @OneToMany(mappedBy = "cliente")
   // private List<Inscripcion> inscripciones;

    // Constructor vacío (OBLIGATORIO para JPA)
    public Cliente() {
    }

    // Constructor sin ID (buena práctica)
    public Cliente(String nombre, String apellido, String dni, int edad, String celular, String domicilio, String lesion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.edad = edad;
        this.celular = celular;
        this.domicilio = domicilio;
        this.lesion = lesion;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni(){
         return dni; 
    }

    public void setDni(String dni){
        this.dni = dni;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getLesion() {
        return lesion;
    }

    public void setLesion(String lesion) {
        this.lesion = lesion;
    }

    public boolean isActivo() {
    return activo;
    }

    public void setActivo(boolean activo) {
     this.activo = activo;
    }
}

