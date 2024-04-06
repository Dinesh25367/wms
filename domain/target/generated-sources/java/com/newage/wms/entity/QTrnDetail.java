package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrnDetail is a Querydsl query type for TrnDetail
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrnDetail extends EntityPathBase<TrnDetail> {

    private static final long serialVersionUID = 1752130409L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrnDetail trnDetail = new QTrnDetail("trnDetail");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath boxId = createString("boxId");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QCurrencyMaster currencyMaster;

    public final NumberPath<Double> expQty = createNumber("expQty", Double.class);

    public final QHsCodeMaster hsCodeMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath isBackOrder = createString("isBackOrder");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final NumberPath<Double> rate = createNumber("rate", Double.class);

    public final NumberPath<Double> rqty = createNumber("rqty", Double.class);

    public final QUomMaster rUomMaster;

    public final QSkuMaster skuMaster;

    public final NumberPath<Integer> transactionSlNo = createNumber("transactionSlNo", Integer.class);

    public final StringPath transactionUid = createString("transactionUid");

    public final QTrnDetailAsn trnDetailAsn;

    public final QTrnDetailAsnCustoms trnDetailAsnCustoms;

    public final QTrnDetailLot trnDetailLot;

    public final QTrnDetailQc trnDetailQc;

    public final QTrnDetailSo trnDetailSo;

    public final QTrnHeaderAsn trnHeaderAsn;

    public final QUomMaster uomMaster;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QTrnDetail(String variable) {
        this(TrnDetail.class, forVariable(variable), INITS);
    }

    public QTrnDetail(Path<? extends TrnDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrnDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrnDetail(PathMetadata metadata, PathInits inits) {
        this(TrnDetail.class, metadata, inits);
    }

    public QTrnDetail(Class<? extends TrnDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.currencyMaster = inits.isInitialized("currencyMaster") ? new QCurrencyMaster(forProperty("currencyMaster"), inits.get("currencyMaster")) : null;
        this.hsCodeMaster = inits.isInitialized("hsCodeMaster") ? new QHsCodeMaster(forProperty("hsCodeMaster"), inits.get("hsCodeMaster")) : null;
        this.rUomMaster = inits.isInitialized("rUomMaster") ? new QUomMaster(forProperty("rUomMaster"), inits.get("rUomMaster")) : null;
        this.skuMaster = inits.isInitialized("skuMaster") ? new QSkuMaster(forProperty("skuMaster"), inits.get("skuMaster")) : null;
        this.trnDetailAsn = inits.isInitialized("trnDetailAsn") ? new QTrnDetailAsn(forProperty("trnDetailAsn"), inits.get("trnDetailAsn")) : null;
        this.trnDetailAsnCustoms = inits.isInitialized("trnDetailAsnCustoms") ? new QTrnDetailAsnCustoms(forProperty("trnDetailAsnCustoms"), inits.get("trnDetailAsnCustoms")) : null;
        this.trnDetailLot = inits.isInitialized("trnDetailLot") ? new QTrnDetailLot(forProperty("trnDetailLot"), inits.get("trnDetailLot")) : null;
        this.trnDetailQc = inits.isInitialized("trnDetailQc") ? new QTrnDetailQc(forProperty("trnDetailQc"), inits.get("trnDetailQc")) : null;
        this.trnDetailSo = inits.isInitialized("trnDetailSo") ? new QTrnDetailSo(forProperty("trnDetailSo"), inits.get("trnDetailSo")) : null;
        this.trnHeaderAsn = inits.isInitialized("trnHeaderAsn") ? new QTrnHeaderAsn(forProperty("trnHeaderAsn"), inits.get("trnHeaderAsn")) : null;
        this.uomMaster = inits.isInitialized("uomMaster") ? new QUomMaster(forProperty("uomMaster"), inits.get("uomMaster")) : null;
    }

}

