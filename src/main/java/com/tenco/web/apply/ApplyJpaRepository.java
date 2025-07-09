package com.tenco.web.apply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplyJpaRepository extends JpaRepository<Apply, Integer> {

    @Query("select p from Apply p join fetch p.user u where u.id = :id")
    List<Apply> findByUserId(@Param("id") int id);

    @Query("select p from Apply p join fetch p.announce a where p.announce.id = :id")
    List<Apply> findByAnnounceId(@Param("id") int id);

    @Query("select p from Apply p join fetch p.resume r where p.resume.id = :id")
    List<Apply> findByResumeId(@Param("id") int id);
}