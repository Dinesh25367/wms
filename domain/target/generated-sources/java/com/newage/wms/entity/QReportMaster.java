package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReportMaster is a Querydsl query type for ReportMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReportMaster extends EntityPathBase<ReportMaster> {

    private static final long serialVersionUID = -1890242962L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReportMaster reportMaster = new QReportMaster("reportMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath createdBy = createString("createdBy");

    public final StringPath createdByUser = createString("createdByUser");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QJasperReportTemplates jasperReportTemplate;

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath reportName = createString("reportName");

    public final StringPath screen = createString("screen");

    public final StringPath service = createString("service");

    public final StringPath status = createString("status");

    public final StringPath trade = createString("trade");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QReportMaster(String variable) {
        this(ReportMaster.class, forVariable(variable), INITS);
    }

    public QReportMaster(Path<? extends ReportMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReportMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReportMaster(PathMetadata metadata, PathInits inits) {
        this(ReportMaster.class, metadata, inits);
    }

    public QReportMaster(Class<? extends ReportMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.jasperReportTemplate = inits.isInitialized("jasperReportTemplate") ? new QJasperReportTemplates(forProperty("jasperReportTemplate")) : null;
    }

}

