package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.PagoDTO;
import com.example.demo.entity.Inscripcion;
import com.example.demo.entity.Pago;
import com.example.demo.repository.InscripcionRepository;
import com.example.demo.repository.PagoRepository;


@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final InscripcionRepository inscripcionRepository;

    public PagoService(PagoRepository pagoRepository,
                       InscripcionRepository inscripcionRepository) {

        this.pagoRepository = pagoRepository;
        this.inscripcionRepository = inscripcionRepository;
    }

    // crear pago
    public Pago crear(PagoDTO dto){

        Inscripcion inscripcion = inscripcionRepository.findById(dto.getInscripcionId())
            .orElseThrow(() -> new RuntimeException("Inscripcion no encontrada"));

            // COMPROBAR SI YA ESTÁ PAGADA

        if (pagoRepository.existsByInscripcionId(
                dto.getInscripcionId())) {

            throw new RuntimeException(
                "La inscripción ya está pagada"
            );
        }
        
        Pago pago = new Pago(
            inscripcion,
            dto.getFechaPago(),
            dto.getMonto(),
            dto.getMetodoPago()
        );

        return pagoRepository.save(pago);
    }

    // listar pagos de una inscripcion FILTRAR POR ID DE ESA INSCRIPCION
    public List<Pago> pagosPorInscripcion(Long inscripcionId){
        return pagoRepository.findByInscripcionId(inscripcionId);
    }

    
}