package com.tenco.web.community;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunity is a Querydsl query type for Community
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunity extends EntityPathBase<Community> {

    private static final long serialVersionUID = 1704754456L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunity community = new QCommunity("community");

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    public final DateTimePath<java.sql.Timestamp> createdAt = createDateTime("createdAt", java.sql.Timestamp.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> interestCount = createNumber("interestCount", Integer.class);

    public final ListPath<com.tenco.web.community.liked_post.LikedPost, com.tenco.web.community.liked_post.QLikedPost> likes = this.<com.tenco.web.community.liked_post.LikedPost, com.tenco.web.community.liked_post.QLikedPost>createList("likes", com.tenco.web.community.liked_post.LikedPost.class, com.tenco.web.community.liked_post.QLikedPost.class, PathInits.DIRECT2);

    public final ListPath<com.tenco.web.reply.Reply, com.tenco.web.reply.QReply> replies = this.<com.tenco.web.reply.Reply, com.tenco.web.reply.QReply>createList("replies", com.tenco.web.reply.Reply.class, com.tenco.web.reply.QReply.class, PathInits.DIRECT2);

    public final NumberPath<Integer> replyCount = createNumber("replyCount", Integer.class);

    public final StringPath title = createString("title");

    public final com.tenco.web.user.QUser user;

    public QCommunity(String variable) {
        this(Community.class, forVariable(variable), INITS);
    }

    public QCommunity(Path<? extends Community> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunity(PathMetadata metadata, PathInits inits) {
        this(Community.class, metadata, inits);
    }

    public QCommunity(Class<? extends Community> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.tenco.web.user.QUser(forProperty("user")) : null;
    }

}

