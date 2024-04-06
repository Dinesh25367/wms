package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStateMaster is a Querydsl query type for StateMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStateMaster extends EntityPathBase<StateMaster> {

    private static final long serialVersionUID = 235461019L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStateMaster stateMaster = new QStateMaster("stateMaster");

    public final QAuditable _super = new QAuditable(this);

    public final SetPath<CityMaster, QCityMaster> cities = this.<CityMaster, QCityMaster>createSet("cities", CityMaster.class, QCityMaster.class, PathInits.DIRECT2);

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

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QStateMaster(String variable) {
        this(StateMaster.class, forVariable(variable), INITS);
    }

    public QStateMaster(Path<? extends StateMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStateMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStateMaster(PathMetadata metadata, PathInits inits) {
        this(StateMaster.class, metadata, inits);
    }

    public QStateMaster(Class<? extends StateMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.country = inits.isInitialized("country") ? new QCountryMaster(forProperty("country"), inits.get("country")) : null;
    }

}

