package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQcStatusMaster is a Querydsl query type for QcStatusMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QQcStatusMaster extends EntityPathBase<QcStatusMaster> {

    private static final long serialVersionUID = -774345762L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQcStatusMaster qcStatusMaster = new QQcStatusMaster("qcStatusMaster");

    public final QAuditable _super = new QAuditable(this);

    public final QBranchMaster branchMaster;

    public final StringPath code = createString("code");

    public final QCompanyMaster companyMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QQcStatusMaster(String variable) {
        this(QcStatusMaster.class, forVariable(variable), INITS);
    }

    public QQcStatusMaster(Path<? extends QcStatusMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQcStatusMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQcStatusMaster(PathMetadata metadata, PathInits inits) {
        this(QcStatusMaster.class, metadata, inits);
    }

    public QQcStatusMaster(Class<? extends QcStatusMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
    }

}

