package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTransactionHistory is a Querydsl query type for TransactionHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTransactionHistory extends EntityPathBase<TransactionHistory> {

    private static final long serialVersionUID = 1284416398L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTransactionHistory transactionHistory = new QTransactionHistory("transactionHistory");

    public final QAuditable _super = new QAuditable(this);

    public final DateTimePath<java.util.Date> actualDate = createDateTime("actualDate", java.util.Date.class);

    public final QBranchMaster branchMaster;

    public final StringPath cartonId = createString("cartonId");

    public final QCompanyMaster companyMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QCustomerMaster customerMaster;

    public final QGrnDetail grnDetail;

    public final NumberPath<Double> grossWeight = createNumber("grossWeight", Double.class);

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath inOut = createString("inOut");

    public final QInventoryStatusMaster invStatus;

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final QLocation locationMaster;

    public final StringPath lpnId = createString("lpnId");

    public final NumberPath<Double> netWeight = createNumber("netWeight", Double.class);

    public final StringPath note = createString("note");

    public final NumberPath<Double> qty = createNumber("qty", Double.class);

    public final StringPath referenceId = createString("referenceId");

    public final QSkuMaster skuMaster;

    public final StringPath sourceTransactionNo = createString("sourceTransactionNo");

    public final NumberPath<Integer> sourceTransactionSlNo = createNumber("sourceTransactionSlNo", Integer.class);

    public final StringPath sourceTransactionType = createString("sourceTransactionType");

    public final QTrnHeaderAsn sourceTrnHeaderAsnMaster;

    public final StringPath taskId = createString("taskId");

    public final NumberPath<Integer> taskSlNo = createNumber("taskSlNo", Integer.class);

    public final StringPath taskStatus = createString("taskStatus");

    public final QTransactionLot transactionLotMaster;

    public final StringPath transactionNo = createString("transactionNo");

    public final NumberPath<Integer> transactionSlNo = createNumber("transactionSlNo", Integer.class);

    public final StringPath transactionStatus = createString("transactionStatus");

    public final StringPath transactionType = createString("transactionType");

    public final QTrnHeaderAsn trnHeaderAsnMaster;

    public final QUomMaster uomMaster;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final NumberPath<Double> volume = createNumber("volume", Double.class);

    public final QWareHouseMaster wareHouseMaster;

    public QTransactionHistory(String variable) {
        this(TransactionHistory.class, forVariable(variable), INITS);
    }

    public QTransactionHistory(Path<? extends TransactionHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTransactionHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTransactionHistory(PathMetadata metadata, PathInits inits) {
        this(TransactionHistory.class, metadata, inits);
    }

    public QTransactionHistory(Class<? extends TransactionHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.customerMaster = inits.isInitialized("customerMaster") ? new QCustomerMaster(forProperty("customerMaster"), inits.get("customerMaster")) : null;
        this.grnDetail = inits.isInitialized("grnDetail") ? new QGrnDetail(forProperty("grnDetail"), inits.get("grnDetail")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
        this.invStatus = inits.isInitialized("invStatus") ? new QInventoryStatusMaster(forProperty("invStatus"), inits.get("invStatus")) : null;
        this.locationMaster = inits.isInitialized("locationMaster") ? new QLocation(forProperty("locationMaster"), inits.get("locationMaster")) : null;
        this.skuMaster = inits.isInitialized("skuMaster") ? new QSkuMaster(forProperty("skuMaster"), inits.get("skuMaster")) : null;
        this.sourceTrnHeaderAsnMaster = inits.isInitialized("sourceTrnHeaderAsnMaster") ? new QTrnHeaderAsn(forProperty("sourceTrnHeaderAsnMaster"), inits.get("sourceTrnHeaderAsnMaster")) : null;
        this.transactionLotMaster = inits.isInitialized("transactionLotMaster") ? new QTransactionLot(forProperty("transactionLotMaster"), inits.get("transactionLotMaster")) : null;
        this.trnHeaderAsnMaster = inits.isInitialized("trnHeaderAsnMaster") ? new QTrnHeaderAsn(forProperty("trnHeaderAsnMaster"), inits.get("trnHeaderAsnMaster")) : null;
        this.uomMaster = inits.isInitialized("uomMaster") ? new QUomMaster(forProperty("uomMaster"), inits.get("uomMaster")) : null;
        this.wareHouseMaster = inits.isInitialized("wareHouseMaster") ? new QWareHouseMaster(forProperty("wareHouseMaster"), inits.get("wareHouseMaster")) : null;
    }

}

