package com.example.demo.exception;

public class TokenExpiradoException extends RuntimeException {
    public TokenExpiradoException(String mensaje) {
        super(mensaje);
    }
}