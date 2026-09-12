package com.example.demo.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.exception.*;

@RestControllerAdvice
public class ManejadorGlobalExcepciones {

    @ExceptionHandler(PagoInvalidoException.class)
    public ResponseEntity<Map<String, String>> manejarPagoInvalido(PagoInvalidoException ex) {
        return ResponseEntity.badRequest().body(Map.of("mensaje", ex.getMessage()));
    }

    @ExceptionHandler(InscripcionNoEncontradaException.class)
    public ResponseEntity<Map<String, String>> manejarInscripcionNoEncontrada(InscripcionNoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", ex.getMessage()));
    }
}