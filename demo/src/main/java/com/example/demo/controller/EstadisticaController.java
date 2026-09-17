package com.example.demo.controller;


import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN')") //protegemos este endpoint, para el rol de ADMIN
    /*
    Uso hasAuthority en vez de hasRole porque tu User.getAuthorities() devuelve SimpleGrantedAuthority(role.name())
    la autoridad es literalmente "ADMIN"

    Si en algún momento cambiás getAuthorities() para anteponer "ROLE_", ahí sí correspondería hasRole('ADMIN')
    
    */
    public EstadisticasDTO obtener() {
        return estadisticaService.obtenerEstadisticas();
    }
}
