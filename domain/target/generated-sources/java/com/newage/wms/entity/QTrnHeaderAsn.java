package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrnHeaderAsn is a Querydsl query type for TrnHeaderAsn
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrnHeaderAsn extends EntityPathBase<TrnHeaderAsn> {

    private static final long serialVersionUID = -1555646825L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrnHeaderAsn trnHeaderAsn = new QTrnHeaderAsn("trnHeaderAsn");

    public final QAuditable _super = new QAuditable(this);

    public final QBranchMaster branchMaster;

    public final QCompanyMaster companyMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final StringPath customerInvoiceNo = createString("customerInvoiceNo");

    public final QCustomerMaster customerMaster;

    public final StringPath customerOrderNo = createString("customerOrderNo");

    public final DateTimePath<java.util.Date> expectedReceivingDate = createDateTime("expectedReceivingDate", java.util.Date.class);

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> invoiceDate = createDateTime("invoiceDate", java.util.Date.class);

    public final StringPath isBlindReceipt = createString("isBlindReceipt");

    public final StringPath isConfirmationEdi = createString("isConfirmationEdi");

    public final StringPath isCrossDock = createString("isCrossDock");

    public final StringPath isEdiTransaction = createString("isEdiTransaction");

    public final StringPath isExpiryAllowed = createString("isExpiryAllowed");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final QMovementTypeMaster movementTypeMaster;

    public final StringPath note = createString("note");

    public final DateTimePath<java.util.Date> orderDate = createDateTime("orderDate", java.util.Date.class);

    public final StringPath orderType = createString("orderType");

    public final StringPath priority = createString("priority");

    public final StringPath status = createString("status");

    public final NumberPath<Long> transactionId = createNumber("transactionId", Long.class);

    public final StringPath transactionStatus = createString("transactionStatus");

    public final StringPath transactionType = createString("transactionType");

    public final StringPath transactionUid = createString("transactionUid");

    public final ListPath<TrnDetail, QTrnDetail> trnDetailList = this.<TrnDetail, QTrnDetail>createList("trnDetailList", TrnDetail.class, QTrnDetail.class, PathInits.DIRECT2);

    public final ListPath<TrnHeaderAddlDetails, QTrnHeaderAddlDetails> trnHeaderAddlDetailsList = this.<TrnHeaderAddlDetails, QTrnHeaderAddlDetails>createList("trnHeaderAddlDetailsList", TrnHeaderAddlDetails.class, QTrnHeaderAddlDetails.class, PathInits.DIRECT2);

    public final ListPath<TrnHeaderCustomsAddlDetails, QTrnHeaderCustomsAddlDetails> trnHeaderCustomsAddlDetailsList = this.<TrnHeaderCustomsAddlDetails, QTrnHeaderCustomsAddlDetails>createList("trnHeaderCustomsAddlDetailsList", TrnHeaderCustomsAddlDetails.class, QTrnHeaderCustomsAddlDetails.class, PathInits.DIRECT2);

    public final QTrnHeaderCustomsDocument trnHeaderCustomsDocument;

    public final QTrnHeaderFreight trnHeaderFreight;

    public final QTrnHeaderFreightShipping trnHeaderFreightShipping;

    public final QTrnHeaderParty trnHeaderParty;

    public final QTrnHeaderSo trnHeaderSo;

    public final ListPath<TrnHeaderTransportation, QTrnHeaderTransportation> trnHeaderTransportationList = this.<TrnHeaderTransportation, QTrnHeaderTransportation>createList("trnHeaderTransportationList", TrnHeaderTransportation.class, QTrnHeaderTransportation.class, PathInits.DIRECT2);

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final QWareHouseMaster wareHouseMaster;

    public QTrnHeaderAsn(String variable) {
        this(TrnHeaderAsn.class, forVariable(variable), INITS);
    }

    public QTrnHeaderAsn(Path<? extends TrnHeaderAsn> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrnHeaderAsn(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrnHeaderAsn(PathMetadata metadata, PathInits inits) {
        this(TrnHeaderAsn.class, metadata, inits);
    }

    public QTrnHeaderAsn(Class<? extends TrnHeaderAsn> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.customerMaster = inits.isInitialized("customerMaster") ? new QCustomerMaster(forProperty("customerMaster"), inits.get("customerMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
        this.movementTypeMaster = inits.isInitialized("movementTypeMaster") ? new QMovementTypeMaster(forProperty("movementTypeMaster"), inits.get("movementTypeMaster")) : null;
        this.trnHeaderCustomsDocument = inits.isInitialized("trnHeaderCustomsDocument") ? new QTrnHeaderCustomsDocument(forProperty("trnHeaderCustomsDocument"), inits.get("trnHeaderCustomsDocument")) : null;
        this.trnHeaderFreight = inits.isInitialized("trnHeaderFreight") ? new QTrnHeaderFreight(forProperty("trnHeaderFreight"), inits.get("trnHeaderFreight")) : null;
        this.trnHeaderFreightShipping = inits.isInitialized("trnHeaderFreightShipping") ? new QTrnHeaderFreightShipping(forProperty("trnHeaderFreightShipping"), inits.get("trnHeaderFreightShipping")) : null;
        this.trnHeaderParty = inits.isInitialized("trnHeaderParty") ? new QTrnHeaderParty(forProperty("trnHeaderParty"), inits.get("trnHeaderParty")) : null;
        this.trnHeaderSo = inits.isInitialized("trnHeaderSo") ? new QTrnHeaderSo(forProperty("trnHeaderSo"), inits.get("trnHeaderSo")) : null;
        this.wareHouseMaster = inits.isInitialized("wareHouseMaster") ? new QWareHouseMaster(forProperty("wareHouseMaster"), inits.get("wareHouseMaster")) : null;
    }

}

