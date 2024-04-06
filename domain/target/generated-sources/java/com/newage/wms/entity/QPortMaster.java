package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPortMaster is a Querydsl query type for PortMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPortMaster extends EntityPathBase<PortMaster> {

    private static final long serialVersionUID = 2017595899L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPortMaster portMaster = new QPortMaster("portMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath code = createString("code");

    public final QCountryMaster country;

    public final StringPath countryCode = createString("countryCode");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath status = createString("status");

    public final StringPath transportMode = createString("transportMode");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QPortMaster(String variable) {
        this(PortMaster.class, forVariable(variable), INITS);
    }

    public QPortMaster(Path<? extends PortMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPortMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPortMaster(PathMetadata metadata, PathInits inits) {
        this(PortMaster.class, metadata, inits);
    }

    public QPortMaster(Class<? extends PortMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.country = inits.isInitialized("country") ? new QCountryMaster(forProperty("country"), inits.get("country")) : null;
    }

}

