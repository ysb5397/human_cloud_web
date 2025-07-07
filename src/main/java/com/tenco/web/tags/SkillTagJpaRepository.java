package com.tenco.web.tags;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillTagJpaRepository extends JpaRepository<SkillTag, Integer> {
    Optional<SkillTag> findBySkillTagName(String skillTagName);
}
