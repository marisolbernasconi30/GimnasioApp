package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.PagoDTO;
import com.example.demo.entity.Pago;
import com.example.demo.service.PagoService;

@RestController
@RequestMapping("/pagos")
@CrossOrigin(origins = "http://localhost:5173")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService){
        this.pagoService = pagoService;
    }

    // POST /pagos
    // registrar un pago
    @PostMapping
    public Pago crear(@RequestBody PagoDTO dto){
        return pagoService.crear(dto);
    }

    // GET /pagos/inscripcion/{id}
    // ver pagos de una inscripción
    @GetMapping("/inscripcion/{id}")
    public List<Pago> pagosPorInscripcion(@PathVariable Long id){
        return pagoService.pagosPorInscripcion(id);
    }


    // GET /pagos/inscripcion/{id}
    // ver pagos de una inscripción
    //@GetMapping("/inscripcion/{id}")
    //public List<Pago> pagosPorInscripcion(@PathVariable Long id){
    //    return pagoService.pagosPorInscripcion(id);
   // }


   //---------------------------------------

  
    //--------------------------------------------------

}