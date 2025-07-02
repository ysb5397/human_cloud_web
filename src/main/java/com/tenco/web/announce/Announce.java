package com.tenco.web.announce;

import com.tenco.web.company.Company;
import com.tenco.web.utis.DateUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


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

    private int interestCount;

    public String getStartDate() {
        return DateUtil.timestampFormat(startDate);
    }
    public String getEndDate() {
        return DateUtil.timestampFormat(endDate);
    }

    // 게시글 소유자 확인하는 기능
    public boolean isCOwner(int checkCompanyId) {
        log.info("게시글 소유자 확인 요청 - 작성자 : {}", checkCompanyId);
        return this.company.getId() == checkCompanyId;
    }

}
