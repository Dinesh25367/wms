package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTrnDetailAddlDetail is a Querydsl query type for TrnDetailAddlDetail
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrnDetailAddlDetail extends EntityPathBase<TrnDetailAddlDetail> {

    private static final long serialVersionUID = -1125936027L;

    public static final QTrnDetailAddlDetail trnDetailAddlDetail = new QTrnDetailAddlDetail("trnDetailAddlDetail");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath charValue = createString("charValue");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final DateTimePath<java.util.Date> dateValue = createDateTime("dateValue", java.util.Date.class);

    public final StringPath fieldDataType = createString("fieldDataType");

    public final StringPath fieldName = createString("fieldName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Double> numberValue = createNumber("numberValue", Double.class);

    public final NumberPath<Integer> transactionSlNo = createNumber("transactionSlNo", Integer.class);

    public final StringPath transactionType = createString("transactionType");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QTrnDetailAddlDetail(String variable) {
        super(TrnDetailAddlDetail.class, forVariable(variable));
    }

    public QTrnDetailAddlDetail(Path<? extends TrnDetailAddlDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTrnDetailAddlDetail(PathMetadata metadata) {
        super(TrnDetailAddlDetail.class, metadata);
    }

}

