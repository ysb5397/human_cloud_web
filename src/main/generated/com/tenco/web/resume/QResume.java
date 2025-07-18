package com.tenco.web.resume;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QResume is a Querydsl query type for Resume
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QResume extends EntityPathBase<Resume> {

    private static final long serialVersionUID = 657624386L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QResume resume = new QResume("resume");

    public final StringPath address = createString("address");

    public final ListPath<com.tenco.web.apply.Apply, com.tenco.web.apply.QApply> applies = this.<com.tenco.web.apply.Apply, com.tenco.web.apply.QApply>createList("applies", com.tenco.web.apply.Apply.class, com.tenco.web.apply.QApply.class, PathInits.DIRECT2);

    public final EnumPath<com.tenco.web._core.common.CareerType> careerType = createEnum("careerType", com.tenco.web._core.common.CareerType.class);

    public final DateTimePath<java.sql.Timestamp> createdAt = createDateTime("createdAt", java.sql.Timestamp.class);

    public final StringPath email = createString("email");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final BooleanPath isPublic = createBoolean("isPublic");

    public final StringPath portfolioUrl = createString("portfolioUrl");

    public final ListPath<com.tenco.web.tags.resume_tag.ResumeSkillTag, com.tenco.web.tags.resume_tag.QResumeSkillTag> resumeSkillTags = this.<com.tenco.web.tags.resume_tag.ResumeSkillTag, com.tenco.web.tags.resume_tag.QResumeSkillTag>createList("resumeSkillTags", com.tenco.web.tags.resume_tag.ResumeSkillTag.class, com.tenco.web.tags.resume_tag.QResumeSkillTag.class, PathInits.DIRECT2);

    public final StringPath selfIntroduction = createString("selfIntroduction");

    public final StringPath title = createString("title");

    public final com.tenco.web.user.QUser user;

    public QResume(String variable) {
        this(Resume.class, forVariable(variable), INITS);
    }

    public QResume(Path<? extends Resume> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QResume(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QResume(PathMetadata metadata, PathInits inits) {
        this(Resume.class, metadata, inits);
    }

    public QResume(Class<? extends Resume> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.tenco.web.user.QUser(forProperty("user")) : null;
    }

}

