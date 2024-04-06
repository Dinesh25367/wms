package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTransactionLot is a Querydsl query type for TransactionLot
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTransactionLot extends EntityPathBase<TransactionLot> {

    private static final long serialVersionUID = -1557467317L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTransactionLot transactionLot = new QTransactionLot("transactionLot");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath batch = createString("batch");

    public final StringPath coo = createString("coo");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QCustomerMaster customerMaster;

    public final DateTimePath<java.util.Date> expDate = createDateTime("expDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath lot01 = createString("lot01");

    public final StringPath lot02 = createString("lot02");

    public final StringPath lot03 = createString("lot03");

    public final StringPath lot04 = createString("lot04");

    public final StringPath lot05 = createString("lot05");

    public final StringPath lot06 = createString("lot06");

    public final StringPath lot07 = createString("lot07");

    public final StringPath lot08 = createString("lot08");

    public final StringPath lot09 = createString("lot09");

    public final StringPath lot10 = createString("lot10");

    public final DateTimePath<java.util.Date> mfgDate = createDateTime("mfgDate", java.util.Date.class);

    public final StringPath serialNumber = createString("serialNumber");

    public final QSkuMaster skuMaster;

    public final ListPath<TransactionLotMore, QTransactionLotMore> transactionLotMoreList = this.<TransactionLotMore, QTransactionLotMore>createList("transactionLotMoreList", TransactionLotMore.class, QTransactionLotMore.class, PathInits.DIRECT2);

    public QTransactionLot(String variable) {
        this(TransactionLot.class, forVariable(variable), INITS);
    }

    public QTransactionLot(Path<? extends TransactionLot> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTransactionLot(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTransactionLot(PathMetadata metadata, PathInits inits) {
        this(TransactionLot.class, metadata, inits);
    }

    public QTransactionLot(Class<? extends TransactionLot> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customerMaster = inits.isInitialized("customerMaster") ? new QCustomerMaster(forProperty("customerMaster"), inits.get("customerMaster")) : null;
        this.skuMaster = inits.isInitialized("skuMaster") ? new QSkuMaster(forProperty("skuMaster"), inits.get("skuMaster")) : null;
    }

}

