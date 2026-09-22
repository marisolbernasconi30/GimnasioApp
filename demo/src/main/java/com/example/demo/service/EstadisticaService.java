package com.example.demo.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EstadisticasDTO;
import com.example.demo.entity.enums.TipoEntrenamiento;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.InscripcionRepository;
import com.example.demo.repository.PagoRepository;


@Service
public class EstadisticaService {

    private final InscripcionRepository inscripcionRepository;
    private final ClienteRepository clienteRepository;
    private final PagoRepository pagoRepository;

    public EstadisticaService(InscripcionRepository inscripcionRepository,
                               ClienteRepository clienteRepository,
                               PagoRepository pagoRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.clienteRepository = clienteRepository;
        this.pagoRepository = pagoRepository;
    }

    public EstadisticasDTO obtenerEstadisticas() {

        EstadisticasDTO dto = new EstadisticasDTO();

        LocalDate hoy = LocalDate.now();
        LocalDate inicioMes = hoy.withDayOfMonth(1);
        LocalDate finMes = hoy.withDayOfMonth(hoy.lengthOfMonth());

        // Porcentaje por tipo de entrenamiento
        long totalInscripciones = inscripcionRepository.count();
        Map<String, Double> porcentajes = new LinkedHashMap<>();

        for (TipoEntrenamiento tipo : TipoEntrenamiento.values()) {
            long cantidad = inscripcionRepository.countByTipoEntrenamiento(tipo);
            double porcentaje = totalInscripciones == 0 ? 0 :
                    (cantidad * 100.0) / totalInscripciones;
            porcentajes.put(tipo.name(), porcentaje);
        }
        dto.setPorcentajePorTipoEntrenamiento(porcentajes);

        // Clientes nuevos del mes
        //long totalClientes = clienteRepository.count();
        
        long clientesNuevos = clienteRepository.countByFechaAltaClienteBetween(inicioMes, finMes);
        dto.setCantidadClienteNuevo(clientesNuevos);

        // Clientes dados de baja en el mes
        long clientesBaja = clienteRepository.countByFechaBajaClienteBetween(inicioMes, finMes);
        dto.setCantidadClientesBaja(clientesBaja);

        // Recaudación del mes
        Double total = pagoRepository.sumarRecaudacionEntreFechas(inicioMes, finMes);
        dto.setTotalRecaudado(total != null ? total : 0);

        return dto;
    }
}