package com.kidtask.model;

public class User {
    private String username;
    private String password;
    private String role; // "Child", "Parent", veya "Teacher"

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Bu bilgilere dışarıdan erişmek için getter metodları
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}