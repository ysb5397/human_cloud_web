package com.tenco.web.announce;

import com.tenco.web.company.Company;
import com.tenco.web.utis.DateUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "announce_tb")
@Entity
public class Announce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private String title;
    private String content;
    private String workLocation;

    @CreationTimestamp
    private Timestamp startDate;

    private Timestamp endDate;
    private int interestCount;

    public String getTime() {
        return DateUtil.timestampFormat(startDate);
    }

    public boolean isOwner(int checkCompanyId) {
        return this.company.getId() == checkCompanyId;
    }
}
