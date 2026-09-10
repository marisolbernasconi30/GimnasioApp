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
import com.example.demo.dto.InscripcionResponseDTO;
import com.example.demo.repository.PagoRepository;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final ClienteRepository clienteRepository;
    private final PagoRepository pagoRepository;

    public InscripcionService(InscripcionRepository inscripcionRepository,
                              ClienteRepository clienteRepository,
                              PagoRepository pagoRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.clienteRepository = clienteRepository;
        this.pagoRepository = pagoRepository;
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

public Page<InscripcionResponseDTO> listar(Pageable pageable) {

    Page<Inscripcion> pagina =
            inscripcionRepository.findByActivaTrue(pageable);

    return pagina.map(inscripcion -> {

        boolean pagada =
                pagoRepository.existsByInscripcionId(inscripcion.getId());

        return new InscripcionResponseDTO(inscripcion, pagada);
    });
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

public Page<InscripcionResponseDTO> listarInscripInac(Pageable pageable) {

    return inscripcionRepository.findByActivaFalse(pageable)
            .map(inscripcion -> {

                boolean pagada =
                        pagoRepository.existsByInscripcionId(inscripcion.getId());

                return new InscripcionResponseDTO(inscripcion, pagada);
            });
}

public List<InscripcionResponseDTO> listarVencidas() {

    LocalDate hoy = LocalDate.now();

    return inscripcionRepository.findAll().stream()
            .filter(Inscripcion::isActiva)
            .filter(i -> i.getFechaVencimiento().isBefore(hoy))
            .map(i -> {

                boolean pagada =
                        pagoRepository.existsByInscripcionId(i.getId());

                return new InscripcionResponseDTO(i, pagada);
            })
            .toList();
}
public List<InscripcionResponseDTO> listarPorVencer() {

    LocalDate hoy = LocalDate.now();
    LocalDate limite = hoy.plusDays(7);

    return inscripcionRepository.findAll().stream()
            .filter(Inscripcion::isActiva)
            .filter(i ->
                    !i.getFechaVencimiento().isBefore(hoy)
                    && !i.getFechaVencimiento().isAfter(limite)
            )
            .map(i -> {

                boolean pagada =
                        pagoRepository.existsByInscripcionId(i.getId());

                return new InscripcionResponseDTO(i, pagada);
            })
            .toList();
}
public List<InscripcionResponseDTO> listarAlDia() {

    LocalDate hoy = LocalDate.now();
    LocalDate limite = hoy.plusDays(7);

    return inscripcionRepository.findAll().stream()
            .filter(Inscripcion::isActiva)
            .filter(i ->
                    i.getFechaVencimiento().isAfter(limite)
            )
            .map(i -> {

                boolean pagada =
                        pagoRepository.existsByInscripcionId(i.getId());

                return new InscripcionResponseDTO(i, pagada);
            })
            .toList();
}
}