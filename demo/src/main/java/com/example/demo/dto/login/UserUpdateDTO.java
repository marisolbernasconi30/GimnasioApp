package com.example.demo.dto.login;

import com.example.demo.entity.enums.login.Role;

public class UserUpdateDTO {

    private String firstname;
    private String lastname;
    private Role role;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
