package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Cliente;
import com.example.demo.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository; // Variable de instancia para el repositorio de clientes

    public ClienteService(ClienteRepository clienteRepository){ // Constructor que recibe el repositorio de clientes
        this.clienteRepository = clienteRepository; // Asigna el repositorio a la variable de instancia para su uso en los métodos del servicio
    }

    public List<Cliente> listar(){ // Método para listar todos los clientes
        return clienteRepository.findAll(); // Devuelve una lista de todos los clientes utilizando el método findAll del repositorio
    }

    public Cliente crear(Cliente cliente){ // Método para crear un nuevo cliente
        return clienteRepository.save(cliente); // Guarda el cliente en la base de datos utilizando el método save del repositorio y lo devuelve
    }

    public void eliminar(Long id){ // Método para eliminar un cliente por su ID
        clienteRepository.deleteById(id); // Elimina el cliente de la base de datos utilizando el método deleteById del repositorio
    }

   public Cliente actualizar(Long id, Cliente clienteActualizado) { // Método para actualizar un cliente existente

    Cliente cliente = clienteRepository.findById(id) // Busca el cliente por su ID
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado")); // Si no se encuentra, lanza una excepción

    cliente.setNombre(clienteActualizado.getNombre()); // Actualiza el nombre del cliente con el nuevo valor
    cliente.setApellido(clienteActualizado.getApellido());
    cliente.setEdad(clienteActualizado.getEdad());
    cliente.setCelular(clienteActualizado.getCelular());
    cliente.setDomicilio(clienteActualizado.getDomicilio());
    cliente.setLesion(clienteActualizado.getLesion());

    return clienteRepository.save(cliente); // Guarda el cliente actualizado en la base de datos y lo devuelve
    }
}

