package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Cliente;
import com.example.demo.service.ClienteService;

@RestController 
// Indica que esta clase es un controlador REST.
// Spring la detecta automáticamente y los métodos devolverán datos (JSON) en lugar de vistas HTML.

@RequestMapping("/clientes") 
// Define la ruta base del controlador.
// Todas las URLs de este controller comenzarán con /clientes.

@CrossOrigin(origins = "http://localhost:5173") 
// Habilita CORS (Cross-Origin Resource Sharing).
// Permite que un frontend que corre en http://localhost:5173
// (por ejemplo Vite, React, Vue) pueda hacer peticiones al backend.

public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @GetMapping 
    // Maneja peticiones HTTP GET.
    // Se usa para RECUPERAR datos del servidor.
    // Endpoint resultante: GET /clientes
    public List<Cliente> listarClientes(){
        return clienteService.listar();
    }

    @PostMapping 
    // Maneja peticiones HTTP POST.
    // Se usa para CREAR un nuevo recurso en el servidor.
    // Endpoint resultante: POST /clientes
    public Cliente crearCliente(@RequestBody Cliente cliente){

        // @RequestBody indica que los datos vendrán en el body del request
        // normalmente en formato JSON y Spring los convierte automáticamente
        // a un objeto Cliente.

        return clienteService.crear(cliente);
    }

    @DeleteMapping("/{id}") 
    // Maneja peticiones HTTP DELETE.
    // Se usa para ELIMINAR un recurso.
    // Endpoint resultante: DELETE /clientes/{id}
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id){

        // @PathVariable toma el valor de la URL.
        // Ejemplo: DELETE /clientes/5
        // id = 5

        clienteService.eliminar(id);

        // ResponseEntity permite controlar la respuesta HTTP.
        // noContent() devuelve un status 204 (eliminado correctamente, sin body).
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    // Maneja peticiones HTTP PUT.
    // Se utiliza para ACTUALIZAR un recurso existente.
    // Endpoint final: PUT /clientes/{id}
    public Cliente actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente){

        // @PathVariable obtiene el valor que viene en la URL.
        // Ejemplo: PUT /clientes/5  → id = 5

        // @RequestBody convierte el JSON enviado en el body
        // en un objeto Cliente.

        return clienteService.actualizar(id, cliente);
    }
}

