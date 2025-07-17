package com.tenco.web.companySub;

import com.tenco.web.company.Company;
import com.tenco.web.user.User;
import com.tenco.web.utis.DateUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Table(name = "companysub_tb", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "company_id"})
})
@Entity
public class CompanySub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToStringExclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    @ToStringExclude
    private Company company;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public CompanySub(int id, User user, Company company, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.company = company;
        this.createdAt = createdAt;
    }

    public String getTime() {
        return DateUtil.timestampFormat(createdAt);
    }
}
