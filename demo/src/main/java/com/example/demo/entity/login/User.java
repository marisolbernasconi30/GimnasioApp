package com.example.demo.entity.login;

import java.util.Collection;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.demo.entity.enums.login.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;

import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) }) // para que la tabla se
                                                                                                // llame users y no
                                                                                                // user, ya que user es
                                                                                                // una palabra reservada
                                                                                                // // en sql

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false) // no me inserta un usuario sin username
    String username;
    String firstname;
    String lastname;
    String password;
    @Enumerated(EnumType.STRING) // para que se guarde el nombre del enum y no el numero, ya que si se guarda el
                                 // numero, si se cambia el orden de los enums, se rompe la aplicacion
    Role role;

    @Column(unique = true)
    private String email;
    private boolean activo = true;

    // -------------------------------------------------------
    public User() {
    }

    // Constructor sin ID (buena práctica)
    @Builder
    public User(String username, String firstname, String lastname, String password, Role role, String email) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
        this.email = email;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getfirstname() {
        return firstname;
    }

    public void setfirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getlastname() {
        return lastname;
    }

    public void setlastname(String lastname) {
        this.lastname = lastname;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // -------------------------------------------------------
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name())); // para que me devuelva el rol del usuario como
                                                                 // autoridad, ya que spring security trabaja con
                                                                 // autoridades y no con roles
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // para que la cuenta no expire, ya que si expira, el usuario no puede loguearse
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // para que la cuenta no este bloqueada, ya que si esta bloqueada, el usuario no
                     // puede loguearse
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // para que las credenciales no expiren, ya que si expiran, el usuario no puede
                     // loguearse
    }

    @Override
    public boolean isEnabled() {
        return activo; // para que la cuenta no este deshabilitada, ya que si esta deshabilitada, el
                       // usuario no puede loguearse
    }

    @Override
    public @Nullable String getPassword() {
        return password; // checkear
    }

    @Override
    public String getUsername() {
        return username; // checkear
    }

}
