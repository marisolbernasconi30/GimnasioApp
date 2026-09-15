package com.example.demo.repository.login;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.login.User;


public interface UserRepository extends JpaRepository<User, Long>  {
     public Optional<User> findByUsername (String username);

    
}
