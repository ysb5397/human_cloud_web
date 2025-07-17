package com.tenco.web.companySub;

import com.tenco.web.company.Company;
import com.tenco.web.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanySubJpaRepository extends JpaRepository<CompanySub, Integer> {

    @Query("select s from CompanySub s join fetch s.company c where c.id = :id")
    List<CompanySub> findByCompany(@Param("id") int id);

    @Query("select case when count(c) > 0 then true else false end " +
            "from CompanySub c where c.company = :company and c.user = :user")
    boolean existsByCompanyAndUser(@Param("company")Company company, @Param("user")User user);
}
