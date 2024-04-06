package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCityMaster is a Querydsl query type for CityMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCityMaster extends EntityPathBase<CityMaster> {

    private static final long serialVersionUID = 1865300837L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCityMaster cityMaster = new QCityMaster("cityMaster");

    public final QAuditable _super = new QAuditable(this);

    public final QCountryMaster country;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final QStateMaster state;

    public final StringPath status = createString("status");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QCityMaster(String variable) {
        this(CityMaster.class, forVariable(variable), INITS);
    }

    public QCityMaster(Path<? extends CityMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCityMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCityMaster(PathMetadata metadata, PathInits inits) {
        this(CityMaster.class, metadata, inits);
    }

    public QCityMaster(Class<? extends CityMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.country = inits.isInitialized("country") ? new QCountryMaster(forProperty("country"), inits.get("country")) : null;
        this.state = inits.isInitialized("state") ? new QStateMaster(forProperty("state"), inits.get("state")) : null;
    }

}

