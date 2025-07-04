package com.tenco.web.announce;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnnounceJpaRepository extends JpaRepository<Announce, Integer> {

    // 게시글 ID로 한방에 기업 id도 들고오기

    @Query("SELECT a FROM Announce a JOIN FETCH a.company c WHERE a.id = :id")
    Optional<Announce> findByIdJoinCompany(@Param("id") int id);

    @Query("SELECT a FROM Announce a WHERE a.company.id = :companyId ORDER BY a.id DESC")
    List<Announce> findAllByCompanyId(@Param("companyId") int companyId);
}


