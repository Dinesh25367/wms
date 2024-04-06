package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrnDetailLot is a Querydsl query type for TrnDetailLot
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrnDetailLot extends EntityPathBase<TrnDetailLot> {

    private static final long serialVersionUID = 979542824L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrnDetailLot trnDetailLot = new QTrnDetailLot("trnDetailLot");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath batch = createString("batch");

    public final StringPath coo = createString("coo");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

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

    public final NumberPath<Integer> transactionSlNo = createNumber("transactionSlNo", Integer.class);

    public final QTrnDetail trnDetail;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QTrnDetailLot(String variable) {
        this(TrnDetailLot.class, forVariable(variable), INITS);
    }

    public QTrnDetailLot(Path<? extends TrnDetailLot> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrnDetailLot(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrnDetailLot(PathMetadata metadata, PathInits inits) {
        this(TrnDetailLot.class, metadata, inits);
    }

    public QTrnDetailLot(Class<? extends TrnDetailLot> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.trnDetail = inits.isInitialized("trnDetail") ? new QTrnDetail(forProperty("trnDetail"), inits.get("trnDetail")) : null;
    }

}

