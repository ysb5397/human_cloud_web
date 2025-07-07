package com.tenco.web.tags.resume_tag;

import com.tenco.web._core.errors.exception.Exception404;
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
    public void save(int resumeId, String skillTagName) { // 1. 변수명을 명확하게 변경
        // 2. ID가 아닌, 이름으로 SkillTag 객체를 DB에서 찾아옵니다.
        SkillTag skillTagEntity = skillTagJpaRepository.findBySkillTagName(skillTagName)
                .orElseThrow(() -> new Exception404("'" + skillTagName + "' 태그를 찾을 수 없습니다."));
        // findBySkillTagName 메소드가 Repository에 정의되어 있어야 합니다.

        // 3. 이력서(Resume) 엔티티를 찾아옵니다.
        Resume resumeEntity = resumeJpaRepository.findById(resumeId)
                .orElseThrow(() -> new Exception404("이력서를 찾을 수 없습니다."));

        // 4. 연관 관계를 맺어줍니다.
        ResumeSkillTag resumeSkillTag = new ResumeSkillTag();
        resumeSkillTag.setResume(resumeEntity);
        resumeSkillTag.setSkillTag(skillTagEntity); // 5. 문자열이 아닌, 찾아온 SkillTag '객체'를 설정

        // 6. 저장합니다.
        resumeSkillTagJpaRepository.save(resumeSkillTag);
    }
}
