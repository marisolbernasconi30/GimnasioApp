package com.example.demo.config.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // ESTE ES EL IMPORT CLAVE
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.repository.login.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor

public class ApplicationConfig {
    
    private final UserRepository userRepository;
    
       
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); //esto es solo un ejemplo, en una aplicación real se debería implementar la lógica de autenticación
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // 1. Instancia la clase concreta (sin null en el constructor)
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService());
        
        // 2. Ahora sí debería reconocer los métodos setter
        //authProvider.setUserDetailsService();
        authProvider.setPasswordEncoder(passwordEncoder());
        
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //esto es solo un ejemplo, en una aplicación real se debería implementar la lógica de autenticación

    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")); //esto es solo un ejemplo, en una aplicación real se debería implementar la lógica de autenticación
    }

}
