package com.tenco.web.resume;

import com.tenco.web.user.User;
import com.tenco.web.utis.DateUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "resume_tb")
@Entity
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;
    private String portfolioUrl;

    @Column(nullable = false)
    private String selfIntroduction;

    @Column(nullable = false)
    private Boolean isPublic;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Resume(int id, User user, String title, String portfolioUrl, String selfIntroduction, Boolean isPublic, Timestamp createsAt) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.portfolioUrl = portfolioUrl;
        this.selfIntroduction = selfIntroduction;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
    }
}
