package com.example.demo.service.login;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmailReset(String destinatario, String token) {

        String link = "http://127.0.0.1:5500/frontend/ResetPassword.html?token=" + token;

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Recuperar contraseña - Sistema Gimnasio");
        mensaje.setText(
                "Hacé clic en el siguiente link para restablecer tu contraseña " +
                "(válido por 30 minutos):\n\n" + link +
                "\n\nSi no solicitaste esto, ignorá este mensaje."
        );

        mailSender.send(mensaje);
    }
}
