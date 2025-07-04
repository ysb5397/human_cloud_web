package com.tenco.web.tags.resume_tag;

import com.tenco.web.tags.SkillTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResumSkillTagJpaRepository extends JpaRepository<ResumeSkillTag, Integer> {

    @Query("select r.skillTag from ResumeSkillTag r where r.resume.id = :resumeId ")
    List<SkillTag> findByResumeId(@Param("resumeId") int resumeId);
}
