package com.example.demo.service.login;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.login.PasswordResetToken;
import com.example.demo.entity.login.User;
import com.example.demo.exception.TokenExpiradoException;
import com.example.demo.exception.TokenInvalidoException;
import com.example.demo.repository.login.PasswordResetTokenRepository;
import com.example.demo.repository.login.UserRepository;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public PasswordResetService(UserRepository userRepository,
            PasswordResetTokenRepository tokenRepository,
            PasswordEncoder passwordEncoder,
            MailService mailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    @Transactional
    public void solicitarReset(String email) {

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return;
        }

        User user = userOpt.get();

        tokenRepository.deleteByUser(user);
        tokenRepository.flush(); // ← esta es la línea nueva

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken(
                token, user, LocalDateTime.now().plusMinutes(30));

        tokenRepository.save(resetToken);

        mailService.enviarEmailReset(user.getEmail(), token);
    }

    @Transactional
    public void resetearPassword(String token, String nuevaPassword) {

        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenInvalidoException("El link no es válido o ya fue usado."));

        if (resetToken.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(resetToken);
            throw new TokenExpiradoException("El link expiró. Solicitá uno nuevo.");
        }

        User user = resetToken.getUser();
        user.setpassword(passwordEncoder.encode(nuevaPassword));
        userRepository.save(user);

        tokenRepository.delete(resetToken);
    }
}