package com.example.demo.dto.login;

import com.example.demo.entity.enums.login.Role;
import com.example.demo.entity.login.User;


public class UserResponseDTO {
     private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private Role role;
    private boolean activo;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getusername();
        this.firstname = user.getfirstname();
        this.lastname = user.getlastname();
        this.role = user.getRole();
        this.activo = user.isActivo();
    }

    // getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public Role getRole() { return role; }
    public boolean isActivo() { return activo; }
}
