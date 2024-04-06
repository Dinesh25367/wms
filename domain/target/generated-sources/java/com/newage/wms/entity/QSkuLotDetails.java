package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSkuLotDetails is a Querydsl query type for SkuLotDetails
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSkuLotDetails extends EntityPathBase<SkuLotDetails> {

    private static final long serialVersionUID = 1750994934L;

    public static final QSkuLotDetails skuLotDetails = new QSkuLotDetails("skuLotDetails");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final StringPath fieldName = createString("fieldName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath isMandatory = createString("isMandatory");

    public final StringPath label = createString("label");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QSkuLotDetails(String variable) {
        super(SkuLotDetails.class, forVariable(variable));
    }

    public QSkuLotDetails(Path<? extends SkuLotDetails> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSkuLotDetails(PathMetadata metadata) {
        super(SkuLotDetails.class, metadata);
    }

}

