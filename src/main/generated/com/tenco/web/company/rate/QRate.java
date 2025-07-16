package com.tenco.web.company.rate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRate is a Querydsl query type for Rate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRate extends EntityPathBase<Rate> {

    private static final long serialVersionUID = -435585231L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRate rate = new QRate("rate");

    public final StringPath comment = createString("comment");

    public final com.tenco.web.company.QCompany company;

    public final DateTimePath<java.sql.Timestamp> createdAt = createDateTime("createdAt", java.sql.Timestamp.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> rating = createNumber("rating", Integer.class);

    public final com.tenco.web.user.QUser user;

    public QRate(String variable) {
        this(Rate.class, forVariable(variable), INITS);
    }

    public QRate(Path<? extends Rate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRate(PathMetadata metadata, PathInits inits) {
        this(Rate.class, metadata, inits);
    }

    public QRate(Class<? extends Rate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new com.tenco.web.company.QCompany(forProperty("company")) : null;
        this.user = inits.isInitialized("user") ? new com.tenco.web.user.QUser(forProperty("user")) : null;
    }

}

