package com.tenco.web.tags.resume_tag;

import com.tenco.web.resume.Resume;
import com.tenco.web.tags.SkillTag;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@Table(name = "resume_skill_tag_tb")
@Entity
public class ResumeSkillTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    @ToString.Exclude
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_tag_id", nullable = false)
    @ToString.Exclude
    private SkillTag skillTag;
}
