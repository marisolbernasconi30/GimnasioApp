package com.example.demo.dto.login;

import com.example.demo.entity.enums.login.Role;

public class UserCreateDTO {
    
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Role role;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
