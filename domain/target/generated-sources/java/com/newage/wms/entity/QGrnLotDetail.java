package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGrnLotDetail is a Querydsl query type for GrnLotDetail
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGrnLotDetail extends EntityPathBase<GrnLotDetail> {

    private static final long serialVersionUID = 1436253975L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGrnLotDetail grnLotDetail = new QGrnLotDetail("grnLotDetail");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath batch = createString("batch");

    public final StringPath coo = createString("coo");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final StringPath deleted = createString("deleted");

    public final DateTimePath<java.util.Date> expDate = createDateTime("expDate", java.util.Date.class);

    public final QGrnDetail grnDetail;

    public final QHsCodeMaster hsCodeMaster;

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

    public final NumberPath<Integer> transactionSlNo = createNumber("transactionSlNo", Integer.class);

    public final QTrnDetail trnDetailMaster;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QGrnLotDetail(String variable) {
        this(GrnLotDetail.class, forVariable(variable), INITS);
    }

    public QGrnLotDetail(Path<? extends GrnLotDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGrnLotDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGrnLotDetail(PathMetadata metadata, PathInits inits) {
        this(GrnLotDetail.class, metadata, inits);
    }

    public QGrnLotDetail(Class<? extends GrnLotDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.grnDetail = inits.isInitialized("grnDetail") ? new QGrnDetail(forProperty("grnDetail"), inits.get("grnDetail")) : null;
        this.hsCodeMaster = inits.isInitialized("hsCodeMaster") ? new QHsCodeMaster(forProperty("hsCodeMaster"), inits.get("hsCodeMaster")) : null;
        this.trnDetailMaster = inits.isInitialized("trnDetailMaster") ? new QTrnDetail(forProperty("trnDetailMaster"), inits.get("trnDetailMaster")) : null;
    }

}

