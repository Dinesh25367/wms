package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrnHeaderFreightShipping is a Querydsl query type for TrnHeaderFreightShipping
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrnHeaderFreightShipping extends EntityPathBase<TrnHeaderFreightShipping> {

    private static final long serialVersionUID = -1630741076L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrnHeaderFreightShipping trnHeaderFreightShipping = new QTrnHeaderFreightShipping("trnHeaderFreightShipping");

    public final QAuditable _super = new QAuditable(this);

    public final DateTimePath<java.util.Date> ata = createDateTime("ata", java.util.Date.class);

    public final QCarrierMaster carrierMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final DateTimePath<java.util.Date> eta = createDateTime("eta", java.util.Date.class);

    public final DateTimePath<java.util.Date> houseDocDate = createDateTime("houseDocDate", java.util.Date.class);

    public final StringPath houseDocRef = createString("houseDocRef");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final DateTimePath<java.util.Date> masterDocDate = createDateTime("masterDocDate", java.util.Date.class);

    public final StringPath masterDocRef = createString("masterDocRef");

    public final QOriginMaster originMaster;

    public final QTosMaster tosMaster;

    public final QTrnHeaderAsn trnHeaderAsn;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final QVesselMaster vesselMaster;

    public QTrnHeaderFreightShipping(String variable) {
        this(TrnHeaderFreightShipping.class, forVariable(variable), INITS);
    }

    public QTrnHeaderFreightShipping(Path<? extends TrnHeaderFreightShipping> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrnHeaderFreightShipping(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrnHeaderFreightShipping(PathMetadata metadata, PathInits inits) {
        this(TrnHeaderFreightShipping.class, metadata, inits);
    }

    public QTrnHeaderFreightShipping(Class<? extends TrnHeaderFreightShipping> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.carrierMaster = inits.isInitialized("carrierMaster") ? new QCarrierMaster(forProperty("carrierMaster"), inits.get("carrierMaster")) : null;
        this.originMaster = inits.isInitialized("originMaster") ? new QOriginMaster(forProperty("originMaster"), inits.get("originMaster")) : null;
        this.tosMaster = inits.isInitialized("tosMaster") ? new QTosMaster(forProperty("tosMaster")) : null;
        this.trnHeaderAsn = inits.isInitialized("trnHeaderAsn") ? new QTrnHeaderAsn(forProperty("trnHeaderAsn"), inits.get("trnHeaderAsn")) : null;
        this.vesselMaster = inits.isInitialized("vesselMaster") ? new QVesselMaster(forProperty("vesselMaster"), inits.get("vesselMaster")) : null;
    }

}

