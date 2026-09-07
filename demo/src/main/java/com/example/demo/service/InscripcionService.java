package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.InscripcionDTO;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.Inscripcion;
import com.example.demo.entity.enums.TipoEntrenamiento;
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

    public Page<Inscripcion> listar(Pageable pageable) {
       return inscripcionRepository.findByActivaTrue(pageable);
    }

    //para obtener la lista por id
    public List<Inscripcion> obtenerInscripcionesCliente(Long clienteId) {
        return inscripcionRepository.findByClienteId(clienteId);
    }

//-------------------------------------

    

   public List<Inscripcion> listarActivasPorTipo(TipoEntrenamiento tipo) {
        return inscripcionRepository.findByTipoEntrenamientoAndActivaTrue(tipo);
    }

    public List<Inscripcion> listarInactivasPorTipo(TipoEntrenamiento tipo) {
        return inscripcionRepository.findByTipoEntrenamientoAndActivaFalse(tipo);
    }

//-------------------------------------


    //para cancelar la inscripcion
    public Inscripcion cancelar(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id) 
            .orElseThrow(() -> new RuntimeException("Inscripcion no encontrada")); // buscar la inscripción por id

        inscripcion.setActiva(false);// marcar como inactiva
        inscripcion.setFechaBaja(LocalDate.now());
        return inscripcionRepository.save(inscripcion);
    }

    public Page<Inscripcion> listarInscripInac(Pageable pageable) { //ME DEVUELVE TODAS LAS INSCRIPCIONES INACTIVAS 
        return inscripcionRepository.findByActivaFalse(pageable);
    }
}