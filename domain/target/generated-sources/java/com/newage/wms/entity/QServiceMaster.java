package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QServiceMaster is a Querydsl query type for ServiceMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QServiceMaster extends EntityPathBase<ServiceMaster> {

    private static final long serialVersionUID = -675676481L;

    public static final QServiceMaster serviceMaster = new QServiceMaster("serviceMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath clearance = createString("clearance");

    public final StringPath code = createString("code");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath fullGroupage = createString("fullGroupage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath importExport = createString("importExport");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final BooleanPath primaryService = createBoolean("primaryService");

    public final StringPath status = createString("status");

    public final StringPath transportMode = createString("transportMode");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QServiceMaster(String variable) {
        super(ServiceMaster.class, forVariable(variable));
    }

    public QServiceMaster(Path<? extends ServiceMaster> path) {
        super(path.getType(), path.getMetadata());
    }

    public QServiceMaster(PathMetadata metadata) {
        super(ServiceMaster.class, metadata);
    }

}

