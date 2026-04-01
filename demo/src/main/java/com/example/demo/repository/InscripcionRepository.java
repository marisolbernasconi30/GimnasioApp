package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Inscripcion;


public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    List<Inscripcion> findByActivaTrue();

    List<Inscripcion> findByClienteId(Long clienteId); //esto es para obtener la lista por id CUANDO LLAMO A CADA CLIENTE

    List<Inscripcion> findByActivaFalse(); //esto es para obtener la lista de inscripciones inactivas

    List<Inscripcion> findByTipoEntrenamientoMusculacion(String tipoEntrenamiento); //esto es para obtener la lista por tipo de entrenamiento

    List<Inscripcion> findByTipoEntrenamientoCardio(String tipoEntrenamiento);

    List<Inscripcion> findByTipoEntrenamientoFuncional(String tipoEntrenamiento);

    List<Inscripcion> findByTipoEntrenamientoCrossfit(String tipoEntrenamiento);

    List<Inscripcion> findByTipoEntrenamientoYoga(String tipoEntrenamiento);

    List<Inscripcion> findByTipoEntrenamientoPilates(String tipoEntrenamiento);

}