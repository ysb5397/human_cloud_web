package com.tenco.web.tags.announce_tag;

import com.tenco.web.announce.Announce;
import com.tenco.web.tags.SkillTag;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "announce_skill_tag_tb")
@Builder
@Entity
public class AnnounceSKillTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announce_id", nullable = false)
    @ToString.Exclude
    private Announce announce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_tag_id", nullable = false)
    @ToString.Exclude
    private SkillTag skillTag;
}
