package com.tenco.web.companySub;

import com.tenco.web.company.Company;
import com.tenco.web.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;

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





}
