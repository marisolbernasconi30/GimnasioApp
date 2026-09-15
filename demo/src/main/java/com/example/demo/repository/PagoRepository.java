package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long>{

    List<Pago> findByInscripcionId(Long inscripcionId);
    boolean existsByInscripcionId(Long inscripcionId);
    Optional<Pago> findTopByInscripcionIdOrderByFechaPagoDesc(Long inscripcionId); //devuelve el ultimo pago 

@Query("SELECT SUM(p.monto) FROM Pago p")
Double sumarTotalRecaudado();

@Query("SELECT SUM(p.monto) FROM Pago p WHERE p.fechaPago BETWEEN :desde AND :hasta")
Double sumarRecaudacionEntreFechas(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);


}