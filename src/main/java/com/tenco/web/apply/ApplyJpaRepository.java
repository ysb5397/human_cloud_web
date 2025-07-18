package com.tenco.web.apply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ApplyJpaRepository extends JpaRepository<Apply, Integer> {

    @Query("select p from Apply p join fetch p.user u where u.id = :id")
    List<Apply> findByUserId(@Param("id") int id);

    @Query("select p from Apply p join fetch p.announce a where p.announce.id = :id")
    List<Apply> findByAnnounceId(@Param("id") int id);

    @Query("select p from Apply p join fetch p.resume r where p.resume.id = :id")
    List<Apply> findByResumeId(@Param("id") int id);

    @Query("select p from Apply p join fetch p.announce a where a.company.id = :id")
    List<Apply> findByCompanyId(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("delete from Apply a where a.announce.company.id = :companyId and a.user.id = :userId")
    void deleteByCompanyIdAndUserId(@Param("companyId") int companyId, @Param("userId") int userId);
}