package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVesselMaster is a Querydsl query type for VesselMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVesselMaster extends EntityPathBase<VesselMaster> {

    private static final long serialVersionUID = -295128592L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVesselMaster vesselMaster = new QVesselMaster("vesselMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath callSign = createString("callSign");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imoNumber = createString("imoNumber");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final StringPath shortName = createString("shortName");

    public final StringPath status = createString("status");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final QCountryMaster vesselCountry;

    public QVesselMaster(String variable) {
        this(VesselMaster.class, forVariable(variable), INITS);
    }

    public QVesselMaster(Path<? extends VesselMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVesselMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVesselMaster(PathMetadata metadata, PathInits inits) {
        this(VesselMaster.class, metadata, inits);
    }

    public QVesselMaster(Class<? extends VesselMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vesselCountry = inits.isInitialized("vesselCountry") ? new QCountryMaster(forProperty("vesselCountry"), inits.get("vesselCountry")) : null;
    }

}

