package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTransactionLotMore is a Querydsl query type for TransactionLotMore
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTransactionLotMore extends EntityPathBase<TransactionLotMore> {

    private static final long serialVersionUID = 711000384L;

    public static final QTransactionLotMore transactionLotMore = new QTransactionLotMore("transactionLotMore");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath label = createString("label");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath lotField = createString("lotField");

    public final StringPath value = createString("value");

    public QTransactionLotMore(String variable) {
        super(TransactionLotMore.class, forVariable(variable));
    }

    public QTransactionLotMore(Path<? extends TransactionLotMore> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTransactionLotMore(PathMetadata metadata) {
        super(TransactionLotMore.class, metadata);
    }

}

