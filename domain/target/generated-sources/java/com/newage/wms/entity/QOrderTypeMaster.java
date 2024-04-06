package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderTypeMaster is a Querydsl query type for OrderTypeMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOrderTypeMaster extends EntityPathBase<OrderTypeMaster> {

    private static final long serialVersionUID = -1699842190L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderTypeMaster orderTypeMaster = new QOrderTypeMaster("orderTypeMaster");

    public final QAuditable _super = new QAuditable(this);

    public final QBranchMaster branchMaster;

    public final StringPath code = createString("code");

    public final QCompanyMaster companyMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final StringPath description = createString("description");

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath status = createString("status");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final StringPath warehouseInOut = createString("warehouseInOut");

    public QOrderTypeMaster(String variable) {
        this(OrderTypeMaster.class, forVariable(variable), INITS);
    }

    public QOrderTypeMaster(Path<? extends OrderTypeMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderTypeMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderTypeMaster(PathMetadata metadata, PathInits inits) {
        this(OrderTypeMaster.class, metadata, inits);
    }

    public QOrderTypeMaster(Class<? extends OrderTypeMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
    }

}

