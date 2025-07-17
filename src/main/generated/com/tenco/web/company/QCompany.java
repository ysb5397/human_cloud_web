package com.tenco.web.company;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompany is a Querydsl query type for Company
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompany extends EntityPathBase<Company> {

    private static final long serialVersionUID = 1483781888L;

    public static final QCompany company = new QCompany("company");

    public final StringPath address = createString("address");

    public final StringPath businessRegistrationNumber = createString("businessRegistrationNumber");

    public final StringPath companyName = createString("companyName");

    public final DateTimePath<java.sql.Timestamp> createdAt = createDateTime("createdAt", java.sql.Timestamp.class);

    public final StringPath email = createString("email");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath password = createString("password");

    public final ListPath<com.tenco.web.company.rate.Rate, com.tenco.web.company.rate.QRate> rates = this.<com.tenco.web.company.rate.Rate, com.tenco.web.company.rate.QRate>createList("rates", com.tenco.web.company.rate.Rate.class, com.tenco.web.company.rate.QRate.class, PathInits.DIRECT2);

    public final ListPath<com.tenco.web.userSub.UserSub, com.tenco.web.userSub.QUserSub> subs = this.<com.tenco.web.userSub.UserSub, com.tenco.web.userSub.QUserSub>createList("subs", com.tenco.web.userSub.UserSub.class, com.tenco.web.userSub.QUserSub.class, PathInits.DIRECT2);

    public final StringPath websiteUrl = createString("websiteUrl");

    public QCompany(String variable) {
        super(Company.class, forVariable(variable));
    }

    public QCompany(Path<? extends Company> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompany(PathMetadata metadata) {
        super(Company.class, metadata);
    }

}

