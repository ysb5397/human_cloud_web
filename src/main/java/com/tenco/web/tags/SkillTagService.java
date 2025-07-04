package com.tenco.web.tags;

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
}
