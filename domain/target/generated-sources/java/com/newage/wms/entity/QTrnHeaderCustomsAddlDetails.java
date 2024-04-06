package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrnHeaderCustomsAddlDetails is a Querydsl query type for TrnHeaderCustomsAddlDetails
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrnHeaderCustomsAddlDetails extends EntityPathBase<TrnHeaderCustomsAddlDetails> {

    private static final long serialVersionUID = 1556891098L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrnHeaderCustomsAddlDetails trnHeaderCustomsAddlDetails = new QTrnHeaderCustomsAddlDetails("trnHeaderCustomsAddlDetails");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath charValue = createString("charValue");

    public final QConfigurationMaster configurationMaster;

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

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QTrnHeaderCustomsAddlDetails(String variable) {
        this(TrnHeaderCustomsAddlDetails.class, forVariable(variable), INITS);
    }

    public QTrnHeaderCustomsAddlDetails(Path<? extends TrnHeaderCustomsAddlDetails> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrnHeaderCustomsAddlDetails(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrnHeaderCustomsAddlDetails(PathMetadata metadata, PathInits inits) {
        this(TrnHeaderCustomsAddlDetails.class, metadata, inits);
    }

    public QTrnHeaderCustomsAddlDetails(Class<? extends TrnHeaderCustomsAddlDetails> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.configurationMaster = inits.isInitialized("configurationMaster") ? new QConfigurationMaster(forProperty("configurationMaster"), inits.get("configurationMaster")) : null;
    }

}

