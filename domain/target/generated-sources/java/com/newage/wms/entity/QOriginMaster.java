package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOriginMaster is a Querydsl query type for OriginMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOriginMaster extends EntityPathBase<OriginMaster> {

    private static final long serialVersionUID = -748172576L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOriginMaster originMaster = new QOriginMaster("originMaster");

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

    public QOriginMaster(String variable) {
        this(OriginMaster.class, forVariable(variable), INITS);
    }

    public QOriginMaster(Path<? extends OriginMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOriginMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOriginMaster(PathMetadata metadata, PathInits inits) {
        this(OriginMaster.class, metadata, inits);
    }

    public QOriginMaster(Class<? extends OriginMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.country = inits.isInitialized("country") ? new QCountryMaster(forProperty("country"), inits.get("country")) : null;
    }

}

