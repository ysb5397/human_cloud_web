package com.tenco.web.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "user_tb")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    private String username;
    private String password;

    @Column(unique = true)
    private String email;

    private String address;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public User(int id, String username, String password, String email, String address, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.createdAt = createdAt;
    }
}