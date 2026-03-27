package com.personal_finance_manager_api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @org.hibernate.annotations.CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private java.util.Date createdAt;

    @org.hibernate.annotations.UpdateTimestamp
    @Column(name = "updated_at")
    private java.util.Date updatedAt;

    // Relationships
    @OneToMany(mappedBy = "user")
    private java.util.List<Transaction> transactions;

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public java.util.Date getCreatedAt() { return createdAt; }
    public java.util.Date getUpdatedAt() { return updatedAt; }
}