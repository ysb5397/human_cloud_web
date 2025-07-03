package com.tenco.web.resume;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ResumeJpaRepository extends JpaRepository<Resume, Integer> {

    @Query("select  r from Resume r join fetch r.user u where r.id = :id")
    Optional<Resume> findByResumeList(@Param("id") int id);
}
