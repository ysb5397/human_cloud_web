package com.tenco.web.community.liked_post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikedPost is a Querydsl query type for LikedPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikedPost extends EntityPathBase<LikedPost> {

    private static final long serialVersionUID = -766710750L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikedPost likedPost = new QLikedPost("likedPost");

    public final com.tenco.web.community.QCommunity community;

    public final DateTimePath<java.sql.Timestamp> createdAt = createDateTime("createdAt", java.sql.Timestamp.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> interestCount = createNumber("interestCount", Integer.class);

    public final com.tenco.web.user.QUser user;

    public QLikedPost(String variable) {
        this(LikedPost.class, forVariable(variable), INITS);
    }

    public QLikedPost(Path<? extends LikedPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikedPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikedPost(PathMetadata metadata, PathInits inits) {
        this(LikedPost.class, metadata, inits);
    }

    public QLikedPost(Class<? extends LikedPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.community = inits.isInitialized("community") ? new com.tenco.web.community.QCommunity(forProperty("community"), inits.get("community")) : null;
        this.user = inits.isInitialized("user") ? new com.tenco.web.user.QUser(forProperty("user")) : null;
    }

}

