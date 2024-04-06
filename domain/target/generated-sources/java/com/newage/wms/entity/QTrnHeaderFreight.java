package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrnHeaderFreight is a Querydsl query type for TrnHeaderFreight
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrnHeaderFreight extends EntityPathBase<TrnHeaderFreight> {

    private static final long serialVersionUID = -1547150882L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrnHeaderFreight trnHeaderFreight = new QTrnHeaderFreight("trnHeaderFreight");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath chargeablePalletCount = createString("chargeablePalletCount");

    public final StringPath chargeableVolume = createString("chargeableVolume");

    public final StringPath chargeableWeight = createString("chargeableWeight");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final StringPath freightApportion = createString("freightApportion");

    public final StringPath freightCurrencyAmount = createString("freightCurrencyAmount");

    public final QCurrencyMaster freightCurrencyMaster;

    public final StringPath freightCurrencyRate = createString("freightCurrencyRate");

    public final QShipmentHeader freightRefShipmentMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath insuranceApportion = createString("insuranceApportion");

    public final StringPath insuranceCurrencyAmount = createString("insuranceCurrencyAmount");

    public final QCurrencyMaster insuranceCurrencyMaster;

    public final StringPath insuranceCurrencyRate = createString("insuranceCurrencyRate");

    public final StringPath isOurJob = createString("isOurJob");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath selfLife = createString("selfLife");

    public final StringPath service = createString("service");

    public final StringPath trade = createString("trade");

    public final QTrnHeaderAsn trnHeaderAsn;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QTrnHeaderFreight(String variable) {
        this(TrnHeaderFreight.class, forVariable(variable), INITS);
    }

    public QTrnHeaderFreight(Path<? extends TrnHeaderFreight> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrnHeaderFreight(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrnHeaderFreight(PathMetadata metadata, PathInits inits) {
        this(TrnHeaderFreight.class, metadata, inits);
    }

    public QTrnHeaderFreight(Class<? extends TrnHeaderFreight> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.freightCurrencyMaster = inits.isInitialized("freightCurrencyMaster") ? new QCurrencyMaster(forProperty("freightCurrencyMaster"), inits.get("freightCurrencyMaster")) : null;
        this.freightRefShipmentMaster = inits.isInitialized("freightRefShipmentMaster") ? new QShipmentHeader(forProperty("freightRefShipmentMaster")) : null;
        this.insuranceCurrencyMaster = inits.isInitialized("insuranceCurrencyMaster") ? new QCurrencyMaster(forProperty("insuranceCurrencyMaster"), inits.get("insuranceCurrencyMaster")) : null;
        this.trnHeaderAsn = inits.isInitialized("trnHeaderAsn") ? new QTrnHeaderAsn(forProperty("trnHeaderAsn"), inits.get("trnHeaderAsn")) : null;
    }

}

