package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTrnHeaderTransportation is a Querydsl query type for TrnHeaderTransportation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrnHeaderTransportation extends EntityPathBase<TrnHeaderTransportation> {

    private static final long serialVersionUID = -182646159L;

    public static final QTrnHeaderTransportation trnHeaderTransportation = new QTrnHeaderTransportation("trnHeaderTransportation");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath containerNumber = createString("containerNumber");

    public final StringPath containerType = createString("containerType");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final StringPath driver = createString("driver");

    public final StringPath driverId = createString("driverId");

    public final StringPath driverMobile = createString("driverMobile");

    public final StringPath gatePassNumber = createString("gatePassNumber");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath isOurTransport = createString("isOurTransport");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath seal = createString("seal");

    public final StringPath vehicleNumber = createString("vehicleNumber");

    public final StringPath vehicleType = createString("vehicleType");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QTrnHeaderTransportation(String variable) {
        super(TrnHeaderTransportation.class, forVariable(variable));
    }

    public QTrnHeaderTransportation(Path<? extends TrnHeaderTransportation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTrnHeaderTransportation(PathMetadata metadata) {
        super(TrnHeaderTransportation.class, metadata);
    }

}

