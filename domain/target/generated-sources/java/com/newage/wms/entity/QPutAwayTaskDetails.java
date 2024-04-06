package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPutAwayTaskDetails is a Querydsl query type for PutAwayTaskDetails
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPutAwayTaskDetails extends EntityPathBase<PutAwayTaskDetails> {

    private static final long serialVersionUID = 1332166104L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPutAwayTaskDetails putAwayTaskDetails = new QPutAwayTaskDetails("putAwayTaskDetails");

    public final QAuditable _super = new QAuditable(this);

    public final QBranchMaster branchMaster;

    public final StringPath cartonId = createString("cartonId");

    public final QCompanyMaster companyMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final StringPath eanupc = createString("eanupc");

    public final QGrnDetail grnDetail;

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath lpnId = createString("lpnId");

    public final NumberPath<Double> putAwayQty = createNumber("putAwayQty", Double.class);

    public final QPutAwayTaskHeader putAwayTaskHeader;

    public final NumberPath<Double> qty = createNumber("qty", Double.class);

    public final QUomMaster ruomMaster;

    public final NumberPath<Double> ruomQty = createNumber("ruomQty", Double.class);

    public final QSkuMaster skuMaster;

    public final StringPath status = createString("status");

    public final QLocation sugLoc;

    public final QLocation toLoc;

    public final QTransactionLot transactionLot;

    public final NumberPath<Integer> transactionSlNo = createNumber("transactionSlNo", Integer.class);

    public final QUomMaster uomMaster;

    public final NumberPath<Double> uomQty = createNumber("uomQty", Double.class);

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final NumberPath<Double> volume = createNumber("volume", Double.class);

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QPutAwayTaskDetails(String variable) {
        this(PutAwayTaskDetails.class, forVariable(variable), INITS);
    }

    public QPutAwayTaskDetails(Path<? extends PutAwayTaskDetails> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPutAwayTaskDetails(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPutAwayTaskDetails(PathMetadata metadata, PathInits inits) {
        this(PutAwayTaskDetails.class, metadata, inits);
    }

    public QPutAwayTaskDetails(Class<? extends PutAwayTaskDetails> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.grnDetail = inits.isInitialized("grnDetail") ? new QGrnDetail(forProperty("grnDetail"), inits.get("grnDetail")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
        this.putAwayTaskHeader = inits.isInitialized("putAwayTaskHeader") ? new QPutAwayTaskHeader(forProperty("putAwayTaskHeader"), inits.get("putAwayTaskHeader")) : null;
        this.ruomMaster = inits.isInitialized("ruomMaster") ? new QUomMaster(forProperty("ruomMaster"), inits.get("ruomMaster")) : null;
        this.skuMaster = inits.isInitialized("skuMaster") ? new QSkuMaster(forProperty("skuMaster"), inits.get("skuMaster")) : null;
        this.sugLoc = inits.isInitialized("sugLoc") ? new QLocation(forProperty("sugLoc"), inits.get("sugLoc")) : null;
        this.toLoc = inits.isInitialized("toLoc") ? new QLocation(forProperty("toLoc"), inits.get("toLoc")) : null;
        this.transactionLot = inits.isInitialized("transactionLot") ? new QTransactionLot(forProperty("transactionLot"), inits.get("transactionLot")) : null;
        this.uomMaster = inits.isInitialized("uomMaster") ? new QUomMaster(forProperty("uomMaster"), inits.get("uomMaster")) : null;
    }

}

