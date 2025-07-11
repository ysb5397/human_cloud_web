package com.tenco.web.tags.announce_tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnounceSkillTagJpaRepository extends JpaRepository<AnnounceSKillTag, Integer> {

    @Query("select a from AnnounceSKillTag a where a.announce.id = :announceId ")
    List<AnnounceSKillTag> findByAnnounceId(@Param("announceId") int announceId);

    void deleteByAnnounceId(int announceId);
}
