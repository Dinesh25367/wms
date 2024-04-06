package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrnDetailAsn is a Querydsl query type for TrnDetailAsn
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrnDetailAsn extends EntityPathBase<TrnDetailAsn> {

    private static final long serialVersionUID = 979532371L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrnDetailAsn trnDetailAsn = new QTrnDetailAsn("trnDetailAsn");

    public final QAuditable _super = new QAuditable(this);

    public final NumberPath<Double> actualLineTotalAmount = createNumber("actualLineTotalAmount", Double.class);

    public final NumberPath<Double> actualQty = createNumber("actualQty", Double.class);

    public final NumberPath<Double> actualRQty = createNumber("actualRQty", Double.class);

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final NumberPath<Double> documentLineTotalAmount = createNumber("documentLineTotalAmount", Double.class);

    public final NumberPath<Double> grossWeight = createNumber("grossWeight", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath lpnId = createString("lpnId");

    public final NumberPath<Double> netWeight = createNumber("netWeight", Double.class);

    public final NumberPath<Integer> transactionSlNo = createNumber("transactionSlNo", Integer.class);

    public final QTrnDetail trnDetail;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final NumberPath<Double> volume = createNumber("volume", Double.class);

    public QTrnDetailAsn(String variable) {
        this(TrnDetailAsn.class, forVariable(variable), INITS);
    }

    public QTrnDetailAsn(Path<? extends TrnDetailAsn> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrnDetailAsn(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrnDetailAsn(PathMetadata metadata, PathInits inits) {
        this(TrnDetailAsn.class, metadata, inits);
    }

    public QTrnDetailAsn(Class<? extends TrnDetailAsn> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.trnDetail = inits.isInitialized("trnDetail") ? new QTrnDetail(forProperty("trnDetail"), inits.get("trnDetail")) : null;
    }

}

