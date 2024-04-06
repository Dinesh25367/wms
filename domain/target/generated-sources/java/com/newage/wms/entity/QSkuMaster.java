package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSkuMaster is a Querydsl query type for SkuMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSkuMaster extends EntityPathBase<SkuMaster> {

    private static final long serialVersionUID = 2121362215L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSkuMaster skuMaster = new QSkuMaster("skuMaster");

    public final QAuditable _super = new QAuditable(this);

    public final QUomMaster baseUnitOfMeasurement;

    public final StringPath batch = createString("batch");

    public final StringPath batchItem = createString("batchItem");

    public final StringPath boxId = createString("boxId");

    public final QBranchMaster branchMaster;

    public final StringPath brandCode = createString("brandCode");

    public final ComparablePath<Character> breakPackForPick = createComparable("breakPackForPick", Character.class);

    public final StringPath code = createString("code");

    public final QCompanyMaster companyMaster;

    public final StringPath coo = createString("coo");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QCurrencyMaster currencyMaster;

    public final QCustomerMaster customerMaster;

    public final StringPath expDate = createString("expDate");

    public final QGroupCompanyMaster groupCompanyMaster;

    public final QHsCodeMaster hsCodeMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QImcoCodeMaster imcoCodeMaster;

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath lovStatus = createString("lovStatus");

    public final StringPath lpnId = createString("lpnId");

    public final StringPath mfgDate = createString("mfgDate");

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final StringPath plantCode = createString("plantCode");

    public final StringPath purchasePrice = createString("purchasePrice");

    public final StringPath rotationBy = createString("rotationBy");

    public final StringPath rotationMethod = createString("rotationMethod");

    public final StringPath serialItem = createString("serialItem");

    public final StringPath serialNo = createString("serialNo");

    public final StringPath skuGroup = createString("skuGroup");

    public final ArrayPath<byte[], Byte> skuImageByte = createArray("skuImageByte", byte[].class);

    public final ListPath<SkuLotDetails, QSkuLotDetails> skuLotDetailsList = this.<SkuLotDetails, QSkuLotDetails>createList("skuLotDetailsList", SkuLotDetails.class, QSkuLotDetails.class, PathInits.DIRECT2);

    public final ListPath<SkuPackDetail, QSkuPackDetail> skuPackDetailList = this.<SkuPackDetail, QSkuPackDetail>createList("skuPackDetailList", SkuPackDetail.class, QSkuPackDetail.class, PathInits.DIRECT2);

    public final StringPath skuSubGroup = createString("skuSubGroup");

    public final QStorageAreaMaster storageAreaMaster;

    public final QStorageTypeMaster storageTypeMaster;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QSkuMaster(String variable) {
        this(SkuMaster.class, forVariable(variable), INITS);
    }

    public QSkuMaster(Path<? extends SkuMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSkuMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSkuMaster(PathMetadata metadata, PathInits inits) {
        this(SkuMaster.class, metadata, inits);
    }

    public QSkuMaster(Class<? extends SkuMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.baseUnitOfMeasurement = inits.isInitialized("baseUnitOfMeasurement") ? new QUomMaster(forProperty("baseUnitOfMeasurement"), inits.get("baseUnitOfMeasurement")) : null;
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.currencyMaster = inits.isInitialized("currencyMaster") ? new QCurrencyMaster(forProperty("currencyMaster"), inits.get("currencyMaster")) : null;
        this.customerMaster = inits.isInitialized("customerMaster") ? new QCustomerMaster(forProperty("customerMaster"), inits.get("customerMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
        this.hsCodeMaster = inits.isInitialized("hsCodeMaster") ? new QHsCodeMaster(forProperty("hsCodeMaster"), inits.get("hsCodeMaster")) : null;
        this.imcoCodeMaster = inits.isInitialized("imcoCodeMaster") ? new QImcoCodeMaster(forProperty("imcoCodeMaster"), inits.get("imcoCodeMaster")) : null;
        this.storageAreaMaster = inits.isInitialized("storageAreaMaster") ? new QStorageAreaMaster(forProperty("storageAreaMaster"), inits.get("storageAreaMaster")) : null;
        this.storageTypeMaster = inits.isInitialized("storageTypeMaster") ? new QStorageTypeMaster(forProperty("storageTypeMaster"), inits.get("storageTypeMaster")) : null;
    }

}

