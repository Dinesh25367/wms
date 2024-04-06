package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QShipmentHeader is a Querydsl query type for ShipmentHeader
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QShipmentHeader extends EntityPathBase<ShipmentHeader> {

    private static final long serialVersionUID = -1398206337L;

    public static final QShipmentHeader shipmentHeader = new QShipmentHeader("shipmentHeader");

    public final QAuditable _super = new QAuditable(this);

    public final NumberPath<Long> branchId = createNumber("branchId", Long.class);

    public final StringPath businessType = createString("businessType");

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final StringPath creationMode = createString("creationMode");

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public final StringPath customerName = createString("customerName");

    public final NumberPath<Long> destinationId = createNumber("destinationId", Long.class);

    public final StringPath destinationName = createString("destinationName");

    public final StringPath direct = createString("direct");

    public final NumberPath<Long> divisionId = createNumber("divisionId", Long.class);

    public final StringPath freight = createString("freight");

    public final NumberPath<Long> groupCompanyId = createNumber("groupCompanyId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath notes = createString("notes");

    public final NumberPath<Long> originId = createNumber("originId", Long.class);

    public final StringPath originName = createString("originName");

    public final StringPath routed = createString("routed");

    public final NumberPath<Long> routedById = createNumber("routedById", Long.class);

    public final StringPath serviceCode = createString("serviceCode");

    public final StringPath serviceType = createString("serviceType");

    public final DateTimePath<java.util.Date> shipmentDate = createDateTime("shipmentDate", java.util.Date.class);

    public final StringPath shipmentUid = createString("shipmentUid");

    public final StringPath status = createString("status");

    public final NumberPath<Long> tosId = createNumber("tosId", Long.class);

    public final StringPath tosName = createString("tosName");

    public final NumberPath<Long> tradeCode = createNumber("tradeCode", Long.class);

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QShipmentHeader(String variable) {
        super(ShipmentHeader.class, forVariable(variable));
    }

    public QShipmentHeader(Path<? extends ShipmentHeader> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShipmentHeader(PathMetadata metadata) {
        super(ShipmentHeader.class, metadata);
    }

}

