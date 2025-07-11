package com.tenco.web.tags.announce_tag;

import com.tenco.web._core.errors.exception.Exception404;
import com.tenco.web.announce.Announce;
import com.tenco.web.tags.SkillTag;
import com.tenco.web.tags.SkillTagJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnnounceSKillTagService {
    private final AnnounceSkillTagJpaRepository announceSkillTagJpaRepository;
    private final SkillTagJpaRepository skillTagJpaRepository;

    public List<AnnounceSKillTag> findByAnnounceId(int announceId) {
        return announceSkillTagJpaRepository.findByAnnounceId(announceId);
    }

    public void save(Announce savedAnnounce, Integer skillId) {
        SkillTag skill = skillTagJpaRepository.findById(skillId)
                .orElseThrow(() -> new Exception404("존재하지 않는 스킬입니다."));

        AnnounceSKillTag announceSkillTag = AnnounceSKillTag.builder()
                .announce(savedAnnounce)
                .skillTag(skill)
                .build();

        announceSkillTagJpaRepository.save(announceSkillTag);
    }

    public void deleteByAnnounceId(int id) {
        announceSkillTagJpaRepository.deleteByAnnounceId(id);
    }
}
