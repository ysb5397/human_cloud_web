package com.tenco.web.announce;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnnounce is a Querydsl query type for Announce
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnnounce extends EntityPathBase<Announce> {

    private static final long serialVersionUID = -1530582398L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnnounce announce = new QAnnounce("announce");

    public final ListPath<com.tenco.web.tags.announce_tag.AnnounceSKillTag, com.tenco.web.tags.announce_tag.QAnnounceSKillTag> announceSkillTags = this.<com.tenco.web.tags.announce_tag.AnnounceSKillTag, com.tenco.web.tags.announce_tag.QAnnounceSKillTag>createList("announceSkillTags", com.tenco.web.tags.announce_tag.AnnounceSKillTag.class, com.tenco.web.tags.announce_tag.QAnnounceSKillTag.class, PathInits.DIRECT2);

    public final EnumPath<com.tenco.web._core.common.CareerType> careerType = createEnum("careerType", com.tenco.web._core.common.CareerType.class);

    public final com.tenco.web.company.QCompany company;

    public final StringPath content = createString("content");

    public final DateTimePath<java.sql.Timestamp> endDate = createDateTime("endDate", java.sql.Timestamp.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> interestCount = createNumber("interestCount", Integer.class);

    public final DateTimePath<java.sql.Timestamp> startDate = createDateTime("startDate", java.sql.Timestamp.class);

    public final StringPath title = createString("title");

    public final StringPath workLocation = createString("workLocation");

    public QAnnounce(String variable) {
        this(Announce.class, forVariable(variable), INITS);
    }

    public QAnnounce(Path<? extends Announce> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnnounce(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnnounce(PathMetadata metadata, PathInits inits) {
        this(Announce.class, metadata, inits);
    }

    public QAnnounce(Class<? extends Announce> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new com.tenco.web.company.QCompany(forProperty("company")) : null;
    }

}

