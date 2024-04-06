package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTimeZoneMaster is a Querydsl query type for TimeZoneMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTimeZoneMaster extends EntityPathBase<TimeZoneMaster> {

    private static final long serialVersionUID = -837777517L;

    public static final QTimeZoneMaster timeZoneMaster = new QTimeZoneMaster("timeZoneMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath abbreviation = createString("abbreviation");

    public final StringPath countryCode = createString("countryCode");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final StringPath dst = createString("dst");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath status = createString("status");

    public final StringPath std = createString("std");

    public final StringPath zoneName = createString("zoneName");

    public QTimeZoneMaster(String variable) {
        super(TimeZoneMaster.class, forVariable(variable));
    }

    public QTimeZoneMaster(Path<? extends TimeZoneMaster> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTimeZoneMaster(PathMetadata metadata) {
        super(TimeZoneMaster.class, metadata);
    }

}

