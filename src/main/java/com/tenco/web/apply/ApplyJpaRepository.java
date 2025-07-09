package com.tenco.web.apply;

import com.tenco.web.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.*;
import java.util.List;

public interface ApplyJpaRepository extends JpaRepository<Apply, Integer> {

    @Query("select a from Apply a join fetch a.user u where u.id = :id")
    List<Apply> findByUserId(@Param("id") int id);

    @Query("select a from Apply a join fetch a.announce a where a.announce.id = :id")
    List<Apply> findByAnnounceId(@Param("id") int id);

    @Query("select a from Apply a join fetch a.resume r where a.resume.id = :id")
    List<Apply> findByResumeId(@Param("id") int id);
}