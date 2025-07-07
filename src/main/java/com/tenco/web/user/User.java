package com.tenco.web.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Slf4j
@NoArgsConstructor
@Data
@Table(name = "user_tb")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;

    @Column(unique = true)
    private String email;

    private String address;

    @CreationTimestamp
    private Timestamp createdAt;

    @Enumerated(EnumType.STRING)
    private UserCareerType careerType;

    @Builder
    public User(int id, String username, String password, String email, String address, Timestamp createdAt, UserCareerType careerType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.createdAt = createdAt;
        this.careerType = careerType;
    }

    public boolean isOwner(int checkUserId) {
        log.info("회원 소유자 확인 요청 - 작성자 : {}", checkUserId);
        return this.id == checkUserId;
    }
}