package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGrnDetail is a Querydsl query type for GrnDetail
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGrnDetail extends EntityPathBase<GrnDetail> {

    private static final long serialVersionUID = -520765348L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGrnDetail grnDetail = new QGrnDetail("grnDetail");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath cartonId = createString("cartonId");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final StringPath deleted = createString("deleted");

    public final NumberPath<Double> expQtyWhileReceiving = createNumber("expQtyWhileReceiving", Double.class);

    public final QGrnHeader grnHeader;

    public final QGrnLotDetail grnLotDetail;

    public final StringPath height = createString("height");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QInventoryStatusMaster inventoryStatusMaster;

    public final BooleanPath isChecked = createBoolean("isChecked");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath length = createString("length");

    public final QLocation locationMaster;

    public final StringPath lpnId = createString("lpnId");

    public final NumberPath<Double> receivingQty = createNumber("receivingQty", Double.class);

    public final NumberPath<Double> receivingRQty = createNumber("receivingRQty", Double.class);

    public final QUomMaster rUomMaster;

    public final NumberPath<Integer> transactionSlNo = createNumber("transactionSlNo", Integer.class);

    public final QTrnDetail trnDetailMaster;

    public final QTrnHeaderTransportation trnHeaderTransportation;

    public final StringPath unit = createString("unit");

    public final QUomMaster uomMaster;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final NumberPath<Double> volume = createNumber("volume", Double.class);

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public final StringPath width = createString("width");

    public QGrnDetail(String variable) {
        this(GrnDetail.class, forVariable(variable), INITS);
    }

    public QGrnDetail(Path<? extends GrnDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGrnDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGrnDetail(PathMetadata metadata, PathInits inits) {
        this(GrnDetail.class, metadata, inits);
    }

    public QGrnDetail(Class<? extends GrnDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.grnHeader = inits.isInitialized("grnHeader") ? new QGrnHeader(forProperty("grnHeader"), inits.get("grnHeader")) : null;
        this.grnLotDetail = inits.isInitialized("grnLotDetail") ? new QGrnLotDetail(forProperty("grnLotDetail"), inits.get("grnLotDetail")) : null;
        this.inventoryStatusMaster = inits.isInitialized("inventoryStatusMaster") ? new QInventoryStatusMaster(forProperty("inventoryStatusMaster"), inits.get("inventoryStatusMaster")) : null;
        this.locationMaster = inits.isInitialized("locationMaster") ? new QLocation(forProperty("locationMaster"), inits.get("locationMaster")) : null;
        this.rUomMaster = inits.isInitialized("rUomMaster") ? new QUomMaster(forProperty("rUomMaster"), inits.get("rUomMaster")) : null;
        this.trnDetailMaster = inits.isInitialized("trnDetailMaster") ? new QTrnDetail(forProperty("trnDetailMaster"), inits.get("trnDetailMaster")) : null;
        this.trnHeaderTransportation = inits.isInitialized("trnHeaderTransportation") ? new QTrnHeaderTransportation(forProperty("trnHeaderTransportation")) : null;
        this.uomMaster = inits.isInitialized("uomMaster") ? new QUomMaster(forProperty("uomMaster"), inits.get("uomMaster")) : null;
    }

}

