package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import com.example.demo.dto.PagoDTO;
import com.example.demo.entity.Inscripcion;
import com.example.demo.entity.Pago;
import com.example.demo.entity.enums.EstadoPago;
import com.example.demo.exception.*;
import com.example.demo.repository.InscripcionRepository;
import com.example.demo.repository.PagoRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final InscripcionRepository inscripcionRepository;

    public PagoService(PagoRepository pagoRepository,
                       InscripcionRepository inscripcionRepository) {

        this.pagoRepository = pagoRepository;
        this.inscripcionRepository = inscripcionRepository;
    }

 @Transactional
    public Pago crear(PagoDTO dto) {

    Inscripcion inscripcion = inscripcionRepository.findById(dto.getInscripcionId())
            .orElseThrow(() -> new InscripcionNoEncontradaException(dto.getInscripcionId()));

    EstadoPago estado = PagoEstadoCalculator.calcular(
            inscripcion.getFechaVencimiento(), dto.getFechaPago()
    );

    if (estado == EstadoPago.AL_DIA) {
        throw new PagoInvalidoException(
                "La inscripción está al día. Se puede volver a pagar desde " +
                inscripcion.getFechaVencimiento().minusDays(7)
        );
    }

    LocalDate nuevaFechaVencimiento = dto.getFechaPago().plusMonths(1);
    inscripcion.setFechaVencimiento(nuevaFechaVencimiento);
    inscripcionRepository.save(inscripcion);

    Pago pago = new Pago(inscripcion, dto.getFechaPago(), dto.getMonto(), dto.getMetodoPago());
    return pagoRepository.save(pago);
}

    // listar pagos de una inscripcion FILTRAR POR ID DE ESA INSCRIPCION
    public List<Pago> pagosPorInscripcion(Long inscripcionId){
        return pagoRepository.findByInscripcionId(inscripcionId);
    }

    
}