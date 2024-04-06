package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrnDetailQc is a Querydsl query type for TrnDetailQc
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrnDetailQc extends EntityPathBase<TrnDetailQc> {

    private static final long serialVersionUID = 170145627L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrnDetailQc trnDetailQc = new QTrnDetailQc("trnDetailQc");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final NumberPath<Double> failedQty = createNumber("failedQty", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QAuthUserProfile inspectedByMaster;

    public final NumberPath<Double> inspectedPackQty = createNumber("inspectedPackQty", Double.class);

    public final NumberPath<Double> inspectedPieceQty = createNumber("inspectedPieceQty", Double.class);

    public final QQcStatusMaster inspectionStatusMaster;

    public final NumberPath<Double> inspectPackQty = createNumber("inspectPackQty", Double.class);

    public final NumberPath<Double> inspectPieceQty = createNumber("inspectPieceQty", Double.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath qcNote = createString("qcNote");

    public final StringPath qcRequired = createString("qcRequired");

    public final StringPath qcResult = createString("qcResult");

    public final NumberPath<Integer> transactionSlNo = createNumber("transactionSlNo", Integer.class);

    public final QTrnDetail trnDetail;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QTrnDetailQc(String variable) {
        this(TrnDetailQc.class, forVariable(variable), INITS);
    }

    public QTrnDetailQc(Path<? extends TrnDetailQc> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrnDetailQc(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrnDetailQc(PathMetadata metadata, PathInits inits) {
        this(TrnDetailQc.class, metadata, inits);
    }

    public QTrnDetailQc(Class<? extends TrnDetailQc> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inspectedByMaster = inits.isInitialized("inspectedByMaster") ? new QAuthUserProfile(forProperty("inspectedByMaster")) : null;
        this.inspectionStatusMaster = inits.isInitialized("inspectionStatusMaster") ? new QQcStatusMaster(forProperty("inspectionStatusMaster"), inits.get("inspectionStatusMaster")) : null;
        this.trnDetail = inits.isInitialized("trnDetail") ? new QTrnDetail(forProperty("trnDetail"), inits.get("trnDetail")) : null;
    }

}

