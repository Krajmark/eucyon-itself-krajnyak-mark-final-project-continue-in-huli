package com.greenfoxacademy.ebayclone.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserCreationDTO {
    @Size(min = 6, message = "Username have to be longer than 5")
    @NotBlank(message = "Username cannot be blank")
    @NotNull(message = "Username cannot be null")
    private String username;
    @Size(min = 6, message = "Password have to be longer than 5")
    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be null")
    private String password;

    public UserCreationDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserCreationDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
