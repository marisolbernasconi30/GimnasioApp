package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Cliente;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.service.ClienteService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:5173") // esta anotacion es para habilitar el intercambio de recursos entre CORDS (origenes) en controladores REST. 
                                               // permiten al navegador web solicitar recursos de un dominio diferente al servidor backend
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping //los metodos Get, son los que REGUPERAN los datos
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @PostMapping //el metodo Post CREA un nuevo recurso, se lo envia al servidor. 
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);  //esto es a la vuelta (bbdd, entity, service, dto, controller, json, cliente), por eso usa el metodo save
    }

    @PutMapping("path/{id}") //checkear si esto esta bien 
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {
        // process PUT request

        return entity;
    }

    @DeleteMapping("/clientes/eliminado") //asi se crea un delete 
    public ResponseEntity<Void> eliminarClientes(@PathVariable Long id) {
        ClienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}