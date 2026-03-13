package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.InscripcionDTO;
import com.example.demo.entity.Inscripcion;
import com.example.demo.service.InscripcionService;

@RestController
@RequestMapping("/inscripciones")
@CrossOrigin(origins = "http://localhost:5173")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService){
        this.inscripcionService = inscripcionService;
    }

    // GET /inscripciones
    // Lista inscripciones activas
    @GetMapping
    public List<Inscripcion> listar(){
        return inscripcionService.listar();
    }

    // POST /inscripciones
    // Crea una nueva inscripción
   @PostMapping
    public Inscripcion crear(@RequestBody InscripcionDTO dto){
    return inscripcionService.crear(dto);
    }

  
  //GET /inscripciones/cliente/{id}

  
@GetMapping("/cliente/{id}")
public List<Inscripcion> obtenerInscripcionesCliente(@PathVariable Long id){
    return inscripcionService.obtenerInscripcionesCliente(id);
}


  // PUT /inscripciones/{id}/cancelar
// Cancela una inscripción (la marca como inactiva)

@PutMapping("/{id}/cancelar")
public Inscripcion cancelar(@PathVariable Long id){
    return inscripcionService.cancelar(id);
}

}