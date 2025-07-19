package com.tenco.web.company.rate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RateJpaRepository extends JpaRepository<Rate, Integer> {

    @Query("select r from Rate r where r.user.id = :userId and r.company.id = :companyId ")
    Rate findByUserAndCompany(@Param("userId") int userId,
                                        @Param("companyId") int companyId);

    @Query("select r from Rate r where r.user.id = :userId ")
    List<Rate> findByUserId(@Param("userId") int userId);

    @Transactional
    void deleteByUserIdAndCompanyId(int userId, int companyId);
}
