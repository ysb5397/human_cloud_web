package com.tenco.web.announce;

import com.tenco.web._core.common.CareerType;
import com.tenco.web.company.Company;
import com.tenco.web.tags.announce_tag.AnnounceSKillTag;
import com.tenco.web.utis.DateUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "announce_tb")
@Entity
public class Announce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id")
    private Company company;

    private String title;
    private String content;
    private String workLocation;

    @CreationTimestamp
    private Timestamp startDate;

    private Timestamp endDate;

    @Enumerated(EnumType.STRING)
    private CareerType careerType;

    private int interestCount;

    @Transient
    private Boolean isOwner;

    public Announce(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getStartDate() {
        return DateUtil.timestampFormat(startDate);
    }

    public LocalDateTime getStartDateTime() {
        return startDate.toLocalDateTime();
    }

    // 시간 변환하는 기능
    public String getEndDate() {
        return DateUtil.dateTimeFormat(endDate);
    }

    // 게시글 소유자 확인하는 기능
    public boolean isCOwner(int checkCompanyId) {
        log.info("게시글 소유자 확인 요청 - 작성자 : {}", checkCompanyId);
        return this.company.getId() == checkCompanyId;
    }

    @OrderBy("id desc")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "announce", cascade = CascadeType.ALL)
    @Builder.Default
    private List<AnnounceSKillTag> announceSkillTags = new ArrayList<>();
}
