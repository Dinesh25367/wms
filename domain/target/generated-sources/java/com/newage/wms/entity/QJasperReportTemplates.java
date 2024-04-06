package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QJasperReportTemplates is a Querydsl query type for JasperReportTemplates
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QJasperReportTemplates extends EntityPathBase<JasperReportTemplates> {

    private static final long serialVersionUID = 355135980L;

    public static final QJasperReportTemplates jasperReportTemplates = new QJasperReportTemplates("jasperReportTemplates");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final ArrayPath<byte[], Byte> file = createArray("file", byte[].class);

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath reportCategory = createString("reportCategory");

    public final StringPath templateName = createString("templateName");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QJasperReportTemplates(String variable) {
        super(JasperReportTemplates.class, forVariable(variable));
    }

    public QJasperReportTemplates(Path<? extends JasperReportTemplates> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJasperReportTemplates(PathMetadata metadata) {
        super(JasperReportTemplates.class, metadata);
    }

}

