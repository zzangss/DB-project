package database.model;

import java.time.LocalDateTime;

public class User {
    private int userId;
    private String email;
    private String password;
    private String name;
    private String role;
    private LocalDateTime createdAt;

    // 기본 생성자
    public User() {}

    // 전체 필드 생성자
    public User(int userId, String email, String password, String name, String role, LocalDateTime createdAt) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.createdAt = createdAt;
    }
    
    public User(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
