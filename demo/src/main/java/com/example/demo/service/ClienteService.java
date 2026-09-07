package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Cliente;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.InscripcionRepository;
import com.example.demo.entity.Inscripcion;
import jakarta.transaction.Transactional;


@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final InscripcionRepository inscripcionRepository;


    public ClienteService(ClienteRepository clienteRepository, InscripcionRepository inscripcionRepository){
        this.clienteRepository = clienteRepository;
        this.inscripcionRepository = inscripcionRepository;
    }

    public List<Cliente> listar(){
        return clienteRepository.findByActivoTrue();
    }

    public List<Cliente> listarBaja(){
        return clienteRepository.findByActivoFalse();
    }



    public Cliente crear(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    
    @Transactional
    public Cliente darDeBaja(Long id){

        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setActivo(false);

        List <Inscripcion> inscripciones = inscripcionRepository.findByClienteId(id);

        // Desactivar todas las inscripciones del cliente
        for (Inscripcion inscripcion : inscripciones) {
            inscripcion.setActiva(false);
        
        }

        inscripcionRepository.saveAll(inscripciones);

        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Long id, Cliente clienteActualizado){

        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setApellido(clienteActualizado.getApellido());
        cliente.setDni(clienteActualizado.getDni());
        cliente.setFechaNacimiento(clienteActualizado.getFechaNacimiento());
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