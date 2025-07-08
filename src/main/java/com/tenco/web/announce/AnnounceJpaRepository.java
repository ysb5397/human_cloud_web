package com.tenco.web.announce;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnnounceJpaRepository extends JpaRepository<Announce, Integer> {

    @Query("SELECT a FROM Announce a JOIN FETCH a.company c WHERE a.id = :id ")
    Optional<Announce> findByIdJoinCompany(@Param("id") int id);

    @Query("SELECT a FROM Announce a WHERE a.company.id = :companyId ORDER BY a.id DESC ")
    List<Announce> findAllByCompanyId(@Param("companyId") int companyId);

    @Query("select a from Announce a where a.title like %:keyword% or a.content like %:keyword% ")
    Page<Announce> findAnnounceByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("select a from Announce a join fetch a.company c order by a.id desc ")
    Page<Announce> findAll(Pageable pageable);
}


