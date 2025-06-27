package com.tenco.web.company;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "company_tb")
@Entity
public class Company {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false)
        private int id;

        private String companyName;
        private String password;
        private String address;

        @Column(unique = true)
        private String businessRegistrationNumber;

        @Column(unique = true)
        private String email;

        @CreationTimestamp
        private Timestamp createdAt;

        @Builder
        public Company(int id, String companyName, String password, String address, String businessRegistrationNumber, String email,  Timestamp createdAt) {
            this.id = id;
            this.companyName = companyName;
            this.password = password;
            this.email = email;
            this.address = address;
            this.businessRegistrationNumber = businessRegistrationNumber;
            this.createdAt = createdAt;
        }
}