package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.InscripcionDTO;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.Inscripcion;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.InscripcionRepository;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final ClienteRepository clienteRepository;

    public InscripcionService(InscripcionRepository inscripcionRepository,
                              ClienteRepository clienteRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.clienteRepository = clienteRepository;
    }

    // Crear una nueva inscripción
    public Inscripcion crear(InscripcionDTO dto) {

        // buscar cliente por id
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // crear inscripción
        Inscripcion inscripcion = new Inscripcion(
                cliente,
                dto.getTipoEntrenamiento(),
                dto.getFechaInicio()
        );

        // guardar en base de datos
        return inscripcionRepository.save(inscripcion);
    }

    public List<Inscripcion> listar() {
       return inscripcionRepository.findByActivaTrue();
    }

    //para obtener la lista por id
    public List<Inscripcion> obtenerInscripcionesCliente(Long clienteId) {
        return inscripcionRepository.findByClienteId(clienteId);
    }

    //para cancelar la inscripcion
    public Inscripcion cancelar(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id) 
            .orElseThrow(() -> new RuntimeException("Inscripcion no encontrada")); // buscar la inscripción por id

        inscripcion.setActiva(false);// marcar como inactiva

        return inscripcionRepository.save(inscripcion);
    }
}