package com.tenco.web.companySub;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompanySub is a Querydsl query type for CompanySub
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompanySub extends EntityPathBase<CompanySub> {

    private static final long serialVersionUID = -1647583486L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompanySub companySub = new QCompanySub("companySub");

    public final com.tenco.web.company.QCompany company;

    public final DateTimePath<java.sql.Timestamp> createdAt = createDateTime("createdAt", java.sql.Timestamp.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final com.tenco.web.user.QUser user;

    public QCompanySub(String variable) {
        this(CompanySub.class, forVariable(variable), INITS);
    }

    public QCompanySub(Path<? extends CompanySub> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompanySub(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompanySub(PathMetadata metadata, PathInits inits) {
        this(CompanySub.class, metadata, inits);
    }

    public QCompanySub(Class<? extends CompanySub> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new com.tenco.web.company.QCompany(forProperty("company")) : null;
        this.user = inits.isInitialized("user") ? new com.tenco.web.user.QUser(forProperty("user")) : null;
    }

}

