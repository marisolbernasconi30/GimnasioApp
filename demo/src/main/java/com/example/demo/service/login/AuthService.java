package com.example.demo.service.login;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.demo.dto.login.AuthResponse;
import com.example.demo.dto.login.LoginRequest;
import com.example.demo.jwt.JwtService;
import com.example.demo.repository.login.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthService(UserRepository userRepository,
                        JwtService jwtService,
                        AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder().token(token).build();
    }

   
}
