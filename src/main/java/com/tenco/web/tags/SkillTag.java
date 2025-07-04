package com.tenco.web.tags;

import com.tenco.web.tags.resume_tag.ResumeSkillTag;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Table(name = "skill_tag_tb")
@Entity
public class SkillTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String skillTagName;

    @Pattern(regexp = "\\d{3}", message = "태그 번호는 3자리 숫자여야 합니다.")
    @Column(nullable = false, length = 3)
    private String skillTagNo;

    @OrderBy("id desc")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "skillTag", cascade = CascadeType.ALL)
    private List<ResumeSkillTag> resumeSkillTags = new ArrayList<>();
}
