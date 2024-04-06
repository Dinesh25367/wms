package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrnHeaderAddlDetails is a Querydsl query type for TrnHeaderAddlDetails
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrnHeaderAddlDetails extends EntityPathBase<TrnHeaderAddlDetails> {

    private static final long serialVersionUID = -1868254990L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrnHeaderAddlDetails trnHeaderAddlDetails = new QTrnHeaderAddlDetails("trnHeaderAddlDetails");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath charValue = createString("charValue");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QCustomerConfigurationMaster customerConfigurationMaster;

    public final DateTimePath<java.util.Date> dateValue = createDateTime("dateValue", java.util.Date.class);

    public final StringPath fieldDataType = createString("fieldDataType");

    public final StringPath fieldName = createString("fieldName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath isMandatory = createString("isMandatory");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Double> numberValue = createNumber("numberValue", Double.class);

    public final StringPath transactionType = createString("transactionType");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QTrnHeaderAddlDetails(String variable) {
        this(TrnHeaderAddlDetails.class, forVariable(variable), INITS);
    }

    public QTrnHeaderAddlDetails(Path<? extends TrnHeaderAddlDetails> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrnHeaderAddlDetails(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrnHeaderAddlDetails(PathMetadata metadata, PathInits inits) {
        this(TrnHeaderAddlDetails.class, metadata, inits);
    }

    public QTrnHeaderAddlDetails(Class<? extends TrnHeaderAddlDetails> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customerConfigurationMaster = inits.isInitialized("customerConfigurationMaster") ? new QCustomerConfigurationMaster(forProperty("customerConfigurationMaster"), inits.get("customerConfigurationMaster")) : null;
    }

}

