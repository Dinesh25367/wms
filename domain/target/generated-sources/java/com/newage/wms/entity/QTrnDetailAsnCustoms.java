package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrnDetailAsnCustoms is a Querydsl query type for TrnDetailAsnCustoms
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrnDetailAsnCustoms extends EntityPathBase<TrnDetailAsnCustoms> {

    private static final long serialVersionUID = 1463110991L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrnDetailAsnCustoms trnDetailAsnCustoms = new QTrnDetailAsnCustoms("trnDetailAsnCustoms");

    public final QAuditable _super = new QAuditable(this);

    public final NumberPath<Double> casePcb = createNumber("casePcb", Double.class);

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final NumberPath<Double> ftaValue = createNumber("ftaValue", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Double> outerCartonPcb = createNumber("outerCartonPcb", Double.class);

    public final NumberPath<Double> packetPcb = createNumber("packetPcb", Double.class);

    public final NumberPath<Double> pcsQty = createNumber("pcsQty", Double.class);

    public final NumberPath<Integer> transactionSlNo = createNumber("transactionSlNo", Integer.class);

    public final QTrnDetail trnDetail;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QTrnDetailAsnCustoms(String variable) {
        this(TrnDetailAsnCustoms.class, forVariable(variable), INITS);
    }

    public QTrnDetailAsnCustoms(Path<? extends TrnDetailAsnCustoms> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrnDetailAsnCustoms(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrnDetailAsnCustoms(PathMetadata metadata, PathInits inits) {
        this(TrnDetailAsnCustoms.class, metadata, inits);
    }

    public QTrnDetailAsnCustoms(Class<? extends TrnDetailAsnCustoms> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.trnDetail = inits.isInitialized("trnDetail") ? new QTrnDetail(forProperty("trnDetail"), inits.get("trnDetail")) : null;
    }

}

