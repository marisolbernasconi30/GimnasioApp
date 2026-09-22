package com.example.demo.controller.login;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.login.AuthResponse;
import com.example.demo.dto.login.ForgotPasswordRequest;
import com.example.demo.dto.login.LoginRequest;
import com.example.demo.dto.login.ResetPasswordRequest;
import com.example.demo.service.login.AuthService;
import com.example.demo.service.login.PasswordResetService;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;

    public AuthController(AuthService authService, PasswordResetService passwordResetService) {
        this.authService = authService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        passwordResetService.solicitarReset(request.getEmail());
        return ResponseEntity.ok(Map.of(
                "mensaje", "Si el email está registrado, vas a recibir un link para restablecer tu contraseña."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordRequest request) {
        passwordResetService.resetearPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada correctamente."));
    }
    /*
     * ESTO ES UN CONTROLADOR DE AUTENTIFICACION
     */

}
