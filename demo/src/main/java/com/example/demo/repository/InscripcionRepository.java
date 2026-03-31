package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Inscripcion;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    List<Inscripcion> findByActivaTrue();

    List<Inscripcion> findByClienteId(Long clienteId); //esto es para obtener la lista por id

    List<Inscripcion> findByActivaFalse(); //esto es para obtener la lista de inscripciones inactivas

}