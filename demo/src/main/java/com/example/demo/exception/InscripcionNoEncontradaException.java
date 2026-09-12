package com.example.demo.exception;

public class InscripcionNoEncontradaException extends RuntimeException {
    public InscripcionNoEncontradaException(Long id) {
        super("Inscripción no encontrada: " + id);
    }
}
