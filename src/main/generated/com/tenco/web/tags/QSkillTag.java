package com.tenco.web.tags;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSkillTag is a Querydsl query type for SkillTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSkillTag extends EntityPathBase<SkillTag> {

    private static final long serialVersionUID = -101394958L;

    public static final QSkillTag skillTag = new QSkillTag("skillTag");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final ListPath<com.tenco.web.tags.resume_tag.ResumeSkillTag, com.tenco.web.tags.resume_tag.QResumeSkillTag> resumeSkillTags = this.<com.tenco.web.tags.resume_tag.ResumeSkillTag, com.tenco.web.tags.resume_tag.QResumeSkillTag>createList("resumeSkillTags", com.tenco.web.tags.resume_tag.ResumeSkillTag.class, com.tenco.web.tags.resume_tag.QResumeSkillTag.class, PathInits.DIRECT2);

    public final StringPath skillTagName = createString("skillTagName");

    public final StringPath skillTagNo = createString("skillTagNo");

    public QSkillTag(String variable) {
        super(SkillTag.class, forVariable(variable));
    }

    public QSkillTag(Path<? extends SkillTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSkillTag(PathMetadata metadata) {
        super(SkillTag.class, metadata);
    }

}

