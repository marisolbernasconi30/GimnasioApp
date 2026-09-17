package com.example.demo.config.login;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.entity.enums.login.Role;
import com.example.demo.entity.login.User;
import com.example.demo.repository.login.UserRepository;

@Component
public class AdminInicialRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInicialRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        boolean existeAdmin = userRepository.findAll().stream()
                .anyMatch(u -> u.getRole() == Role.ADMIN);

        if (!existeAdmin) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("cambiar123"))
                    .firstname("Administrador")
                    .lastname("Sistema")
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("Usuario ADMIN inicial creado: admin / cambiar123");
        }
    }
}