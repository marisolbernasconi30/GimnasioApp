package com.example.demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EstadisticasDTO;
import com.example.demo.service.EstadisticaService;

@RestController
@RequestMapping("/estadisticas")

public class EstadisticaController {

    private final EstadisticaService estadisticaService;

    public EstadisticaController(EstadisticaService estadisticaService) {
        this.estadisticaService = estadisticaService;
    }

    @GetMapping
    public EstadisticasDTO obtener() {
        return estadisticaService.obtenerEstadisticas();
    }
}
