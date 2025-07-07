package com.tenco.web.resume;

import com.tenco.web._core.errors.exception.Exception400;
import com.tenco.web._core.errors.exception.Exception403;
import com.tenco.web._core.errors.exception.Exception404;
import com.tenco.web.tags.SkillTag;
import com.tenco.web.tags.SkillTagService;
import com.tenco.web.tags.resume_tag.ResumeSkillTagRequest;
import com.tenco.web.tags.resume_tag.ResumeSkillTagService;
import com.tenco.web.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ResumeService {

    private static final Logger log = LoggerFactory.getLogger(ResumeService.class);

    private final ResumeJpaRepository resumeJpaRepository;
    private final ResumeSkillTagService resumeSkillTagService;
    private final SkillTagService skillTagService;

    // 이력서 저장 기능
    @Transactional
    public void save(ResumeRequest.SaveDTO saveDTO, User sessionuser) {
        log.info("이력서 저장 서비스 처리 시작 - 유저 id {}", saveDTO.getUserId());

        Resume resume = saveDTO.toEntity(sessionuser);

        resumeJpaRepository.save(resume);
    }

    // 이력서 목록 조회
    public List<Resume> findAll() {
        List<Resume> resumeList = resumeJpaRepository.findAll();
        return resumeList;
    }

    public List<Resume> findByUserId(int id) {
        List<Resume> resumeList = resumeJpaRepository.findByUserId(id);
        return resumeList;
    }

    // 이력서 상세조회
    public Resume findById(int id) {
        Resume resume = resumeJpaRepository.findByResumeList(id)
                .orElseThrow(() -> new IllegalArgumentException("이력서 없음: id = " + id));


        return resume;
    }

    // 상세 보기에서 이력서 삭제
    @Transactional
    public void deleteById(int id, User sessionResume) {
        log.info("이력서 삭제 서비스 시작 - 이력서 ID {}", id);
        Resume resume = resumeJpaRepository.findById(id).orElseThrow(() -> {
            throw new Exception400("삭제할 이력서가 없습니다.");
        });
        if(!resume.isOwner(sessionResume.getId())){
            throw new Exception403("본인이 작성한 이력서만 삭제할 수 있습니다");
        }
        resumeJpaRepository.deleteById(id);
        log.info("이력서 석제 완료 - 이력서 ID {}", id);
    }

    // 이력서 소유자 확인 (수정 화면 요청)
    public void checkResumeOwner(int resumeId, int userId) {
        Resume resume = findById(resumeId);
        if(!resume.isOwner(userId)) {
            throw new Exception403("본인 이력서만 수정할 수 있습니다.");
        }
    }

    public List<ResumeSkillTagRequest.CheckDTO> getSkillTagsForUpdate(int resumeId) {
        // 1. 모든 기술 스택 목록을 가져옵니다.
        List<SkillTag> allSkills = skillTagService.findAll();

        // 2. 현재 이력서에 이미 선택된 기술 스택 목록을 가져옵니다.
        List<SkillTag> selectedSkills = resumeSkillTagService.findByResumeId(resumeId);

        // 3. DTO 리스트를 생성합니다.
        List<ResumeSkillTagRequest.CheckDTO> checkDTOs = allSkills.stream()
                .map(skill -> {
                    // 현재 스킬이 선택된 스킬 목록에 포함되어 있는지 확인합니다.
                    boolean checked = selectedSkills.stream()
                            .anyMatch(s -> s.getId() == skill.getId());
                    return new ResumeSkillTagRequest.CheckDTO(skill.getId(), skill.getSkillTagName(), checked);
                })
                .collect(Collectors.toList());

        return checkDTOs;
    }

    // 이력서 수정
    @Transactional
    public Resume UpdateById(int id, ResumeRequest.UpdateDTO updateDTO,
                             User sessionResume) {
        log.info("이력서 수정 서비스 시작 - 이력서 ID {}", id);
        Resume resume = resumeJpaRepository.findById(id).orElseThrow(() -> {
           log.warn("이력서 조회 실패 - ID {}", id);
           return new Exception404("해당 이력서가 존재하지 않습니다");
        });

        if(!resume.isOwner(sessionResume.getId())) {
            throw new Exception403("본인 이력서만 수정 가능합니다.");
        }

        resume.setTitle(updateDTO.getTitle());
        resume.setPortfolioUrl(updateDTO.getPortfolioUrl());
        resume.setSelfIntroduction(updateDTO.getSelfIntroduction());

        resumeSkillTagService.deleteByResumeId(id);

        if (updateDTO.getSkillTags() != null && !updateDTO.getSkillTags().isEmpty()) {
            updateDTO.getSkillTags().forEach(skillTag -> {
                resumeSkillTagService.save(id, skillTag);
            });
        }

        log.info("이력서 수정 완료 - 이력서 ID {}, 이력서 제목 {}", id, resume.getTitle());
        return resume;
    }

}
