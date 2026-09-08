package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long>{

    List<Pago> findByInscripcionId(Long inscripcionId);
boolean existsByInscripcionId(Long inscripcionId);
}