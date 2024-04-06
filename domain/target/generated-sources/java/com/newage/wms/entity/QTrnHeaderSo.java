package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrnHeaderSo is a Querydsl query type for TrnHeaderSo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrnHeaderSo extends EntityPathBase<TrnHeaderSo> {

    private static final long serialVersionUID = -1989844255L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrnHeaderSo trnHeaderSo = new QTrnHeaderSo("trnHeaderSo");

    public final QAuditable _super = new QAuditable(this);

    public final QShipmentHeader bookingRefShipmentMaster;

    public final StringPath chargeablePalletCount = createString("chargeablePalletCount");

    public final StringPath chargeableVolume = createString("chargeableVolume");

    public final StringPath chargeableWeight = createString("chargeableWeight");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath isOurJob = createString("isOurJob");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath orderCurrencyAmount = createString("orderCurrencyAmount");

    public final QCurrencyMaster orderCurrencyMaster;

    public final StringPath orderCurrencyRate = createString("orderCurrencyRate");

    public final StringPath selfLife = createString("selfLife");

    public final StringPath service = createString("service");

    public final StringPath trade = createString("trade");

    public final QTrnHeaderAsn trnHeaderAsn;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QTrnHeaderSo(String variable) {
        this(TrnHeaderSo.class, forVariable(variable), INITS);
    }

    public QTrnHeaderSo(Path<? extends TrnHeaderSo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrnHeaderSo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrnHeaderSo(PathMetadata metadata, PathInits inits) {
        this(TrnHeaderSo.class, metadata, inits);
    }

    public QTrnHeaderSo(Class<? extends TrnHeaderSo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bookingRefShipmentMaster = inits.isInitialized("bookingRefShipmentMaster") ? new QShipmentHeader(forProperty("bookingRefShipmentMaster")) : null;
        this.orderCurrencyMaster = inits.isInitialized("orderCurrencyMaster") ? new QCurrencyMaster(forProperty("orderCurrencyMaster"), inits.get("orderCurrencyMaster")) : null;
        this.trnHeaderAsn = inits.isInitialized("trnHeaderAsn") ? new QTrnHeaderAsn(forProperty("trnHeaderAsn"), inits.get("trnHeaderAsn")) : null;
    }

}

