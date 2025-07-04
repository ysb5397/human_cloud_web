package com.tenco.web.tags.resume_tag;

import com.tenco.web.tags.SkillTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ResumeSkillTagService {

    private final ResumSkillTagJpaRepository resumSkillTagJpaRepository;


    public List<SkillTag> findByResumeId(int resumeId) {
        return resumSkillTagJpaRepository.findByResumeId(resumeId);
    }
}
