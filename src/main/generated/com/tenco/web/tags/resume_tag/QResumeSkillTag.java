package com.tenco.web.tags.resume_tag;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QResumeSkillTag is a Querydsl query type for ResumeSkillTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QResumeSkillTag extends EntityPathBase<ResumeSkillTag> {

    private static final long serialVersionUID = -640482829L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QResumeSkillTag resumeSkillTag = new QResumeSkillTag("resumeSkillTag");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final com.tenco.web.resume.QResume resume;

    public final com.tenco.web.tags.QSkillTag skillTag;

    public QResumeSkillTag(String variable) {
        this(ResumeSkillTag.class, forVariable(variable), INITS);
    }

    public QResumeSkillTag(Path<? extends ResumeSkillTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QResumeSkillTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QResumeSkillTag(PathMetadata metadata, PathInits inits) {
        this(ResumeSkillTag.class, metadata, inits);
    }

    public QResumeSkillTag(Class<? extends ResumeSkillTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.resume = inits.isInitialized("resume") ? new com.tenco.web.resume.QResume(forProperty("resume"), inits.get("resume")) : null;
        this.skillTag = inits.isInitialized("skillTag") ? new com.tenco.web.tags.QSkillTag(forProperty("skillTag")) : null;
    }

}

