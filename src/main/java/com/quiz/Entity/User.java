package com.quiz.Entity;

import java.util.List;

import jakarta.persistence.*;



@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // "USER" or "ADMIN"

    @Column(unique = true, nullable = false)
    private String username; // Added username field
    
    private String sessionId; 
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserQuizScore> userQuizScores;

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and Setter for role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public List<UserQuizScore> getUserQuizScores() {
        return userQuizScores;
    }

    public void setUserQuizScores(List<UserQuizScore> userQuizScores) {
        this.userQuizScores = userQuizScores;
    }
}

