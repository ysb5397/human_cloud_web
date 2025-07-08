package com.tenco.web.tags.announce_tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnnounceSKillTagService {
    private final AnnounceSkillTagJpaRepository announceSkillTagJpaRepository;

    public List<AnnounceSKillTag> findByAnnounceId(int announceId) {
        return announceSkillTagJpaRepository.findByAnnounceId(announceId);
    }
}
