package com.example.demo.entity.login;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime fechaExpiracion;

    public PasswordResetToken() {
    }

    public PasswordResetToken(String token, User user, LocalDateTime fechaExpiracion) {
        this.token = token;
        this.user = user;
        this.fechaExpiracion = fechaExpiracion;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }
}