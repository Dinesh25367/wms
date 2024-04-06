package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrnHeaderParty is a Querydsl query type for TrnHeaderParty
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrnHeaderParty extends EntityPathBase<TrnHeaderParty> {

    private static final long serialVersionUID = -314655679L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrnHeaderParty trnHeaderParty = new QTrnHeaderParty("trnHeaderParty");

    public final QAuditable _super = new QAuditable(this);

    public final QPartyAddressDetail consigneeAddressDetail;

    public final QCustomerMaster consigneeMaster;

    public final StringPath consigneeName = createString("consigneeName");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QPartyAddressDetail forwarderAddressDetail;

    public final QCustomerMaster forwarderMaster;

    public final StringPath forwarderName = createString("forwarderName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final QPartyAddressDetail shipperAddressDetail;

    public final QCustomerMaster shipperMaster;

    public final StringPath shipperName = createString("shipperName");

    public final QPartyAddressDetail transporterAddressDetail;

    public final QCustomerMaster transporterMaster;

    public final StringPath transporterName = createString("transporterName");

    public final QTrnHeaderAsn trnHeaderAsn;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QTrnHeaderParty(String variable) {
        this(TrnHeaderParty.class, forVariable(variable), INITS);
    }

    public QTrnHeaderParty(Path<? extends TrnHeaderParty> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrnHeaderParty(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrnHeaderParty(PathMetadata metadata, PathInits inits) {
        this(TrnHeaderParty.class, metadata, inits);
    }

    public QTrnHeaderParty(Class<? extends TrnHeaderParty> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.consigneeAddressDetail = inits.isInitialized("consigneeAddressDetail") ? new QPartyAddressDetail(forProperty("consigneeAddressDetail"), inits.get("consigneeAddressDetail")) : null;
        this.consigneeMaster = inits.isInitialized("consigneeMaster") ? new QCustomerMaster(forProperty("consigneeMaster"), inits.get("consigneeMaster")) : null;
        this.forwarderAddressDetail = inits.isInitialized("forwarderAddressDetail") ? new QPartyAddressDetail(forProperty("forwarderAddressDetail"), inits.get("forwarderAddressDetail")) : null;
        this.forwarderMaster = inits.isInitialized("forwarderMaster") ? new QCustomerMaster(forProperty("forwarderMaster"), inits.get("forwarderMaster")) : null;
        this.shipperAddressDetail = inits.isInitialized("shipperAddressDetail") ? new QPartyAddressDetail(forProperty("shipperAddressDetail"), inits.get("shipperAddressDetail")) : null;
        this.shipperMaster = inits.isInitialized("shipperMaster") ? new QCustomerMaster(forProperty("shipperMaster"), inits.get("shipperMaster")) : null;
        this.transporterAddressDetail = inits.isInitialized("transporterAddressDetail") ? new QPartyAddressDetail(forProperty("transporterAddressDetail"), inits.get("transporterAddressDetail")) : null;
        this.transporterMaster = inits.isInitialized("transporterMaster") ? new QCustomerMaster(forProperty("transporterMaster"), inits.get("transporterMaster")) : null;
        this.trnHeaderAsn = inits.isInitialized("trnHeaderAsn") ? new QTrnHeaderAsn(forProperty("trnHeaderAsn"), inits.get("trnHeaderAsn")) : null;
    }

}

