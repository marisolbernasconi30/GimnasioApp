package com.example.demo.service;

import java.time.LocalDate;
import com.example.demo.entity.enums.EstadoPago;
import org.springframework.stereotype.Service;

@Service
public class PagoEstadoCalculator {
    
    private static final int DIAS_POR_VENCER = 7;

    public static EstadoPago calcular(LocalDate fechaVencimiento, LocalDate hoy) {

        if (fechaVencimiento == null) {
            return EstadoPago.VENCIDA;
        }

        LocalDate inicioPorVencer = fechaVencimiento.minusDays(DIAS_POR_VENCER);

        if (hoy.isAfter(fechaVencimiento)) {
            return EstadoPago.VENCIDA;
        } else if (!hoy.isBefore(inicioPorVencer)) {
            return EstadoPago.POR_VENCER;
        } else {
            return EstadoPago.AL_DIA;
        }
    }

}
