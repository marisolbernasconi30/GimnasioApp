package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Inscripcion;
import com.example.demo.entity.enums.TipoEntrenamiento;



public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    List<Inscripcion> findByActivaTrue();

    List<Inscripcion> findByClienteId(Long clienteId); //esto es para obtener la lista por id CUANDO LLAMO A CADA CLIENTE

    List<Inscripcion> findByActivaFalse(); //esto es para obtener la lista de inscripciones inactivas

     List<Inscripcion> findByTipoEntrenamientoAndActivaTrue(TipoEntrenamiento tipoEntrenamiento); //ME TRAE LOS ACTIVOS DE CADA ENTRENAMIENTO

    List<Inscripcion> findByTipoEntrenamientoAndActivaFalse(TipoEntrenamiento tipoEntrenamiento); //ME TRAE LOS INACTIVOS DE CADA ENTRENAMIENTO
}