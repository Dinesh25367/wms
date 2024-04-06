package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrnDetailSo is a Querydsl query type for TrnDetailSo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrnDetailSo extends EntityPathBase<TrnDetailSo> {

    private static final long serialVersionUID = 170145701L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrnDetailSo trnDetailSo = new QTrnDetailSo("trnDetailSo");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath isDamagedAllowed = createString("isDamagedAllowed");

    public final StringPath isExpiredAllowed = createString("isExpiredAllowed");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath pickFromAsn = createString("pickFromAsn");

    public final StringPath pickFromCustomDocRef1 = createString("pickFromCustomDocRef1");

    public final StringPath pickFromCustomDocRef2 = createString("pickFromCustomDocRef2");

    public final StringPath pickFromGrn = createString("pickFromGrn");

    public final StringPath pickFromLocation = createString("pickFromLocation");

    public final StringPath pickFromLpn = createString("pickFromLpn");

    public final StringPath pickFromPo = createString("pickFromPo");

    public final StringPath saleLineTotalValue = createString("saleLineTotalValue");

    public final NumberPath<Integer> transactionSlNo = createNumber("transactionSlNo", Integer.class);

    public final QTrnDetail trnDetail;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QTrnDetailSo(String variable) {
        this(TrnDetailSo.class, forVariable(variable), INITS);
    }

    public QTrnDetailSo(Path<? extends TrnDetailSo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrnDetailSo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrnDetailSo(PathMetadata metadata, PathInits inits) {
        this(TrnDetailSo.class, metadata, inits);
    }

    public QTrnDetailSo(Class<? extends TrnDetailSo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.trnDetail = inits.isInitialized("trnDetail") ? new QTrnDetail(forProperty("trnDetail"), inits.get("trnDetail")) : null;
    }

}

