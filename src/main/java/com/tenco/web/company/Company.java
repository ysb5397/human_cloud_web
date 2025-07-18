package com.tenco.web.company;

import com.tenco.web.company.rate.Rate;
import com.tenco.web.userSub.UserSub;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
@Data
@Table(name = "company_tb")
@Entity
@ToString(exclude = {"rates", "subs"})
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private String websiteUrl;

    @Transient
    private boolean isRated;

    @Transient
    private boolean isSub;

    @Builder
    public Company(int id, String companyName, String password, String address, String businessRegistrationNumber, String email,  Timestamp createdAt, String websiteUrl) {
        this.id = id;
        this.companyName = companyName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.createdAt = createdAt;
        this.websiteUrl = websiteUrl;
    }

    public boolean isOwner(int checkCompanyId) {
        log.info("회원 소유자 확인 요청 - 작성자 : {}", checkCompanyId);
        return this.id == checkCompanyId;
    }

    @OrderBy("id DESC")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.REMOVE)
    List<Rate> rates = new ArrayList<>();

    @OrderBy("id DESC")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.REMOVE)
    List<UserSub> subs = new ArrayList<>();
}