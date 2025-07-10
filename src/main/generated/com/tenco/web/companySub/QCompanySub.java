package com.tenco.web.companySub;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompanySub is a Querydsl query type for CompanySub
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompanySub extends EntityPathBase<CompanySub> {

    private static final long serialVersionUID = -1647583486L;

    public static final QCompanySub companySub = new QCompanySub("companySub");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QCompanySub(String variable) {
        super(CompanySub.class, forVariable(variable));
    }

    public QCompanySub(Path<? extends CompanySub> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompanySub(PathMetadata metadata) {
        super(CompanySub.class, metadata);
    }

}

