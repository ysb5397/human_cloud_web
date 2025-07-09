package com.tenco.web.userSub;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserSub is a Querydsl query type for UserSub
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserSub extends EntityPathBase<UserSub> {

    private static final long serialVersionUID = -1030862096L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserSub userSub = new QUserSub("userSub");

    public final com.tenco.web.company.QCompany company;

    public final DateTimePath<java.sql.Timestamp> createdAt = createDateTime("createdAt", java.sql.Timestamp.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final com.tenco.web.user.QUser user;

    public QUserSub(String variable) {
        this(UserSub.class, forVariable(variable), INITS);
    }

    public QUserSub(Path<? extends UserSub> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserSub(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserSub(PathMetadata metadata, PathInits inits) {
        this(UserSub.class, metadata, inits);
    }

    public QUserSub(Class<? extends UserSub> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new com.tenco.web.company.QCompany(forProperty("company")) : null;
        this.user = inits.isInitialized("user") ? new com.tenco.web.user.QUser(forProperty("user")) : null;
    }

}

