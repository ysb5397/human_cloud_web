package com.tenco.web.tags;

import com.tenco.web._core.errors.exception.Exception404;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SkillTagService {

    private final SkillTagJpaRepository skillTagJpaRepository;

    public List<SkillTag> findAll() {
        return skillTagJpaRepository.findAll();
    }

    public SkillTag findById(Integer skillId) {
        return skillTagJpaRepository.findById(skillId)
                .orElseThrow(() -> {
                    throw new Exception404("존재하지 않는 스킬입니다.");
                });
    }
}
