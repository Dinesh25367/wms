package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTosMaster is a Querydsl query type for TosMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTosMaster extends EntityPathBase<TosMaster> {

    private static final long serialVersionUID = 1215174434L;

    public static final QTosMaster tosMaster = new QTosMaster("tosMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath code = createString("code");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath freightPPCC = createString("freightPPCC");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final NumberPath<Long> ranking = createNumber("ranking", Long.class);

    public final StringPath status = createString("status");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QTosMaster(String variable) {
        super(TosMaster.class, forVariable(variable));
    }

    public QTosMaster(Path<? extends TosMaster> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTosMaster(PathMetadata metadata) {
        super(TosMaster.class, metadata);
    }

}

