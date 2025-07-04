package com.tenco.web.tags.resume_tag;

import com.tenco.web.resume.Resume;
import com.tenco.web.resume.ResumeJpaRepository;
import com.tenco.web.tags.SkillTag;
import com.tenco.web.tags.SkillTagJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ResumeSkillTagService {

    private final ResumeSkillTagJpaRepository resumeSkillTagJpaRepository;
    private final ResumeJpaRepository resumeJpaRepository;
    private final SkillTagJpaRepository skillTagJpaRepository;

    public List<SkillTag> findByResumeId(int resumeId) {
        return resumeSkillTagJpaRepository.findByResumeId(resumeId);
    }

    @Transactional
    public void deleteByResumeId(int resumeId) {
        resumeSkillTagJpaRepository.deleteByResumeId(resumeId);
    }

    @Transactional
    public void save(int resumeId, int skillTagId) {
        Resume resume = resumeJpaRepository.getReferenceById(resumeId);
        SkillTag skillTag = skillTagJpaRepository.getReferenceById(skillTagId);

        ResumeSkillTag resumeSkillTag = new ResumeSkillTag();
        resumeSkillTag.setResume(resume);
        resumeSkillTag.setSkillTag(skillTag);

        resumeSkillTagJpaRepository.save(resumeSkillTag);
    }
}
