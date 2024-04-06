package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMovementTypeMaster is a Querydsl query type for MovementTypeMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMovementTypeMaster extends EntityPathBase<MovementTypeMaster> {

    private static final long serialVersionUID = 1946628099L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMovementTypeMaster movementTypeMaster = new QMovementTypeMaster("movementTypeMaster");

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

    public QMovementTypeMaster(String variable) {
        this(MovementTypeMaster.class, forVariable(variable), INITS);
    }

    public QMovementTypeMaster(Path<? extends MovementTypeMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMovementTypeMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMovementTypeMaster(PathMetadata metadata, PathInits inits) {
        this(MovementTypeMaster.class, metadata, inits);
    }

    public QMovementTypeMaster(Class<? extends MovementTypeMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
    }

}

