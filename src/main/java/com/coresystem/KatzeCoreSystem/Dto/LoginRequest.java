
// =============================================================
// FILE: src/main/java/com/enterprise/enterprisesystem/dto/LoginRequest.java
// Description: Data Transfer Object for login form data.
// =============================================================
package com.coresystem.KatzeCoreSystem.Dto;

// This DTO is more for REST APIs. For Thymeleaf forms, Spring often binds directly to parameters
// or uses model attributes. However, it's still good practice to have this for clarity.
public class LoginRequest {
    private String username;
    private String password;

    // Getters and Setters
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