package com.example.demo.controller.login;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.login.UserCreateDTO;
import com.example.demo.dto.login.UserResponseDTO;
import com.example.demo.dto.login.UserUpdateDTO;
import com.example.demo.service.login.UserService;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponseDTO> listar() {
        return userService.listar();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponseDTO obtenerPorId(@PathVariable Long id) {
        return userService.obtenerPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponseDTO crear(@RequestBody UserCreateDTO dto) {
        return userService.crear(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponseDTO actualizar(@PathVariable Long id, @RequestBody UserUpdateDTO dto) {
        return userService.actualizar(id, dto);
    }

    @PutMapping("/{id}/baja")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void darDeBaja(@PathVariable Long id) {
        userService.darDeBaja(id);
    }
}