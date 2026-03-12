package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Cliente;
import com.example.demo.service.ClienteService;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    // GET /clientes
    // Devuelve la lista de clientes activos
    @GetMapping
    public List<Cliente> listarClientes(){
        return clienteService.listar();
    }

    // GET /clientes/{id}
    // Devuelve un cliente específico según su ID
    @GetMapping("/{id}")
    public Cliente obtenerCliente(@PathVariable Long id){
        return clienteService.obtenerPorId(id);
    }

    // POST /clientes
    // Crea un nuevo cliente en la base de datos
    @PostMapping
    public Cliente crearCliente(@RequestBody Cliente cliente){
        return clienteService.crear(cliente);
    }

    // PUT /clientes/{id}
    // Actualiza los datos de un cliente existente
    @PutMapping("/{id}")
    public Cliente actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente){
        return clienteService.actualizar(id, cliente);
    }

    // PUT /clientes/{id}/baja
    // Da de baja al cliente (baja lógica, no se elimina de la base)
    @PutMapping("/{id}/baja")
    public Cliente darDeBaja(@PathVariable Long id){
        return clienteService.darDeBaja(id);
    }


}

