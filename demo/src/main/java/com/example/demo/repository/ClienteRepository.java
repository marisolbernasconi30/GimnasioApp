package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> { 

    // JpaRepository es una interfaz de Spring Data JPA que proporciona métodos CRUD predefinidos.
    // Al extender JpaRepository, ClienteRepository hereda métodos como save(), findAll(), findById(), deleteById(), etc.
    // El primer parámetro (Cliente) es la entidad que maneja el repositorio y el segundo parámetro (Long) es el tipo de dato del ID de la entidad.

List<Cliente> findByActivoTrue(); //metodo para listar los clientes de alta 

List<Cliente> findByActivoFalse(); //metodo para listar los clientes de baja
 Optional<Cliente> findByDni(String dni);

}
