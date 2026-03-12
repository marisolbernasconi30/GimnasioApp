package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Cliente;
import com.example.demo.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listar(){
        return clienteRepository.findByActivoTrue();
    }

    public Cliente crear(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente darDeBaja(Long id){

        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setActivo(false);

        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Long id, Cliente clienteActualizado){

        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setApellido(clienteActualizado.getApellido());
        cliente.setEdad(clienteActualizado.getEdad());
        cliente.setCelular(clienteActualizado.getCelular());
        cliente.setDomicilio(clienteActualizado.getDomicilio());
        cliente.setLesion(clienteActualizado.getLesion());

        return clienteRepository.save(cliente);
    }

   public Cliente obtenerPorId(Long id){
    return clienteRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

    }
}