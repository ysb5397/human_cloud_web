package com.tenco.web.resume;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeJpaRepository extends JpaRepository<Resume, Integer> {
}
