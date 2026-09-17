package com.example.demo.service.login;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.login.UserCreateDTO;
import com.example.demo.dto.login.UserResponseDTO;
import com.example.demo.dto.login.UserUpdateDTO;
import com.example.demo.entity.login.User;
import com.example.demo.repository.login.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> listar() {
        return userRepository.findAll().stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    public UserResponseDTO obtenerPorId(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return new UserResponseDTO(user);
    }

    public UserResponseDTO crear(UserCreateDTO dto) {

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese username");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .role(dto.getRole())
                .build();

        return new UserResponseDTO(userRepository.save(user));
    }

    public UserResponseDTO actualizar(Long id, UserUpdateDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setfirstname(dto.getFirstname());
        user.setlastname(dto.getLastname());
        user.setRole(dto.getRole());

        return new UserResponseDTO(userRepository.save(user));
    }

    public void darDeBaja(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setActivo(false);
        userRepository.save(user);
    }
}