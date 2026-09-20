package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.InscripcionDTO;
import com.example.demo.entity.Cliente;
import com.example.demo.entity.Inscripcion;
import com.example.demo.entity.enums.EstadoPago;
import com.example.demo.entity.enums.TipoEntrenamiento;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.InscripcionRepository;
import com.example.demo.dto.InscripcionResponseDTO;

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
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Inscripcion inscripcion = new Inscripcion(cliente, dto.getTipoEntrenamiento(), dto.getFechaInicio());
        return inscripcionRepository.save(inscripcion);
    }

    public Page<InscripcionResponseDTO> listar(Pageable pageable) {
        return inscripcionRepository.findByActivaTrue(pageable)
                .map(InscripcionResponseDTO::new);
    }

   /*  private boolean pagoVigente(Inscripcion inscripcion) {

        if (inscripcion.getFechaVencimiento() == null) {
            return false;
        }

        return LocalDate.now().isBefore(inscripcion.getFechaVencimiento());
    }*/

    // FIX PRINCIPAL: ahora devuelve DTOs con estado calculado
    public List<InscripcionResponseDTO> obtenerInscripcionesCliente(Long clienteId) {
        return inscripcionRepository.findByClienteId(clienteId).stream()
                .map(InscripcionResponseDTO::new)
                .toList();
    }

    // -------------------------------------

public List<InscripcionResponseDTO> listarActivasPorTipo(TipoEntrenamiento tipo) {
    return inscripcionRepository.findByTipoEntrenamientoAndActivaTrue(tipo).stream()
            .map(InscripcionResponseDTO::new)
            .toList();
}

public List<InscripcionResponseDTO> listarInactivasPorTipo(TipoEntrenamiento tipo) {
    return inscripcionRepository.findByTipoEntrenamientoAndActivaFalse(tipo).stream()
            .map(InscripcionResponseDTO::new)
            .toList();
}


    // -------------------------------------

    // para cancelar la inscripcion
    public Inscripcion cancelar(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripcion no encontrada"));
        inscripcion.setActiva(false);
        inscripcion.setFechaBaja(LocalDate.now());
        return inscripcionRepository.save(inscripcion);
    }

    public Page<InscripcionResponseDTO> listarInscripInac(Pageable pageable) {
        return inscripcionRepository.findByActivaFalse(pageable)
                .map(InscripcionResponseDTO::new);
    }

    public List<InscripcionResponseDTO> listarVencidas() {
        return inscripcionRepository.findAll().stream()
                .filter(Inscripcion::isActiva)
                .map(InscripcionResponseDTO::new)
                .filter(dto -> dto.getEstadoPago() == EstadoPago.VENCIDA)
                .toList();
    }

    public List<InscripcionResponseDTO> listarPorVencer() {
        return inscripcionRepository.findAll().stream()
                .filter(Inscripcion::isActiva)
                .map(InscripcionResponseDTO::new)
                .filter(dto -> dto.getEstadoPago() == EstadoPago.POR_VENCER)
                .toList();
    }

    public List<InscripcionResponseDTO> listarAlDia() {
        return inscripcionRepository.findAll().stream()
                .filter(Inscripcion::isActiva)
                .map(InscripcionResponseDTO::new)
                .filter(dto -> dto.getEstadoPago() == EstadoPago.AL_DIA)
                .toList();
    }
}