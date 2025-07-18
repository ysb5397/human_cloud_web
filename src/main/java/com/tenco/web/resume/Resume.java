package com.tenco.web.resume;

import com.tenco.web._core.common.CareerType;
import com.tenco.web.apply.Apply;
import com.tenco.web.tags.resume_tag.ResumeSkillTag;
import com.tenco.web.user.User;
import com.tenco.web.utis.DateUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
    private String email;
    private String address;
    private String portfolioUrl;

    // @Column(nullable = false)
    private String selfIntroduction;

    // @Column(nullable = false)
    private Boolean isPublic;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Enumerated(EnumType.STRING)
    private CareerType careerType;

    @Transient
    private boolean isApplied;

    @Builder
    public Resume(int id, User user, String title, String email, String address, String portfolioUrl, String selfIntroduction, Boolean isPublic, Timestamp createdAt, CareerType careerType) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.email = email;
        this.address = address;
        this.portfolioUrl = portfolioUrl;
        this.selfIntroduction = selfIntroduction;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.careerType = careerType;
    }

    public boolean isOwner(int checkUserId) {
        log.info("이력서 소유자 확인 요청 - 작성자 : {}", checkUserId);
        return this.user.getId() == checkUserId;
    }

    public String getTime(){
        return DateUtil.timestampFormat(createdAt);
    }

    @OrderBy("id desc")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume", cascade = CascadeType.ALL)
    private List<ResumeSkillTag> resumeSkillTags = new ArrayList<>();

    @OrderBy("id desc")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume", cascade = CascadeType.REMOVE)
    List<Apply> applies = new ArrayList<>();
}
