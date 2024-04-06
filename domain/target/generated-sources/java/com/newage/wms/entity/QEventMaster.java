package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEventMaster is a Querydsl query type for EventMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEventMaster extends EntityPathBase<EventMaster> {

    private static final long serialVersionUID = -849208092L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEventMaster eventMaster = new QEventMaster("eventMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath code = createString("code");

    public final QCountryMaster country;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final StringPath status = createString("status");

    public QEventMaster(String variable) {
        this(EventMaster.class, forVariable(variable), INITS);
    }

    public QEventMaster(Path<? extends EventMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEventMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEventMaster(PathMetadata metadata, PathInits inits) {
        this(EventMaster.class, metadata, inits);
    }

    public QEventMaster(Class<? extends EventMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.country = inits.isInitialized("country") ? new QCountryMaster(forProperty("country"), inits.get("country")) : null;
    }

}

