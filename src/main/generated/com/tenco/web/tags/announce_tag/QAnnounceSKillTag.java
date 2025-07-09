package com.tenco.web.tags.announce_tag;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnnounceSKillTag is a Querydsl query type for AnnounceSKillTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnnounceSKillTag extends EntityPathBase<AnnounceSKillTag> {

    private static final long serialVersionUID = -816767405L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnnounceSKillTag announceSKillTag = new QAnnounceSKillTag("announceSKillTag");

    public final com.tenco.web.announce.QAnnounce announce;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final com.tenco.web.tags.QSkillTag skillTag;

    public QAnnounceSKillTag(String variable) {
        this(AnnounceSKillTag.class, forVariable(variable), INITS);
    }

    public QAnnounceSKillTag(Path<? extends AnnounceSKillTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnnounceSKillTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnnounceSKillTag(PathMetadata metadata, PathInits inits) {
        this(AnnounceSKillTag.class, metadata, inits);
    }

    public QAnnounceSKillTag(Class<? extends AnnounceSKillTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.announce = inits.isInitialized("announce") ? new com.tenco.web.announce.QAnnounce(forProperty("announce"), inits.get("announce")) : null;
        this.skillTag = inits.isInitialized("skillTag") ? new com.tenco.web.tags.QSkillTag(forProperty("skillTag")) : null;
    }

}

