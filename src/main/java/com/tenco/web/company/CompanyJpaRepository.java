package com.tenco.web.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyJpaRepository extends JpaRepository<Company, Integer> {

    // 사업자번호와 비밀번호로 사용자 조회 (로그인용)
    @Query("select c from Company c where c.businessRegistrationNumber = :businessRegistrationNumber and c.password = :password ")
    Optional<Company> findByBusinessRegistrationNumberAndPassword(@Param("businessRegistrationNumber") String businessRegistrationNumber,
                                                                  @Param("password") String password);

    // 회사명으로 사용자 조회(중복체크용)
    @Query("select c from Company c where c.companyName = :companyName ")
    Optional<Company> findByCompanyName(@Param("companyName") String companyName);
}
