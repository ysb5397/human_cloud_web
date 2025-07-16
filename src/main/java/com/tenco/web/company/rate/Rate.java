package com.tenco.web.company.rate;

import com.tenco.web.company.Company;
import com.tenco.web.user.User;
import com.tenco.web.utis.DateUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rate_tb", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "company_id"})})
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id")
    private Company company;

    private int rating;
    private String comment;

    @CreationTimestamp
    private Timestamp createdAt;

    @Transient
    private boolean isOwner;

    public String getTime() {
        return DateUtil.timestampFormat(createdAt);
    }
}
