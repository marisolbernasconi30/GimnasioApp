package com.example.demo.exception;




public class PagoInvalidoException extends RuntimeException {
    public PagoInvalidoException(String mensaje) {
        super(mensaje);
    }
}




