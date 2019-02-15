package com.humbertorovina.clockingsystem.api.security.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class JwtAuthenticationDto {

    private String email;
    private String password;

    public JwtAuthenticationDto() {
    }

    @NotEmpty(message = "Email must not be empty.")
    @Email(message = "Invalid email.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Password must not be empty.")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "JwtAuthenticationRequestDto [email=" + email + ", password=" + password + "]";
    }

}
