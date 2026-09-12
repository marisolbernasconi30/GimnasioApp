package com.example.demo.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import com.example.demo.dto.InscripcionDTO;
import com.example.demo.dto.InscripcionResponseDTO;
import com.example.demo.entity.Inscripcion;
import com.example.demo.entity.enums.TipoEntrenamiento;
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
    // Lista inscripciones ACTIVAS 
    @GetMapping
    public Page<InscripcionResponseDTO> listar(Pageable pageable){
        return inscripcionService.listar(pageable);
    }

    //GET /inscripciones?page=1&size=10

     // Lista inscripciones INACTIVAS 
    @GetMapping ("/inactivas")
    public Page<InscripcionResponseDTO> listarInscripInac(Pageable pageable){
        return inscripcionService.listarInscripInac(pageable);
    }

//-------------------------------------
 
  
    @GetMapping("/tipo/{tipo}/activas")
    public List<Inscripcion> listarActivasPorTipo(@PathVariable TipoEntrenamiento tipo) {
        return inscripcionService.listarActivasPorTipo(tipo);
    }

    @GetMapping("/tipo/{tipo}/inactivas")
    public List<Inscripcion> listarInactivasPorTipo(@PathVariable TipoEntrenamiento tipo) {
        return inscripcionService.listarInactivasPorTipo(tipo);
    }
   


//--------------------------------------


    // POST /inscripciones
    // Crea una nueva inscripción
   @PostMapping
    public Inscripcion crear(@RequestBody InscripcionDTO dto){
    return inscripcionService.crear(dto);
    }

  
  //GET /inscripciones/cliente/{id}

  
@GetMapping("/cliente/{clienteId}")
public List<InscripcionResponseDTO> obtenerInscripcionesCliente(@PathVariable Long clienteId){
    return inscripcionService.obtenerInscripcionesCliente(clienteId);
}


  // PUT /inscripciones/{id}/cancelar
// Cancela una inscripción (la marca como inactiva)

@PutMapping("/{id}/cancelar")
public Inscripcion cancelar(@PathVariable Long id){
    return inscripcionService.cancelar(id);
}

@GetMapping("/vencidas")
public List<InscripcionResponseDTO> listarVencidas() {
    return inscripcionService.listarVencidas();
}
@GetMapping("/por-vencer")
public List<InscripcionResponseDTO> listarPorVencer() {
    return inscripcionService.listarPorVencer();
}
@GetMapping("/al-dia")
public List<InscripcionResponseDTO> listarAlDia() {
    return inscripcionService.listarAlDia();
}

}