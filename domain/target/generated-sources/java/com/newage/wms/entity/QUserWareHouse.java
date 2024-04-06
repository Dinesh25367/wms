package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserWareHouse is a Querydsl query type for UserWareHouse
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserWareHouse extends EntityPathBase<UserWareHouse> {

    private static final long serialVersionUID = 1516589152L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserWareHouse userWareHouse = new QUserWareHouse("userWareHouse");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath asnCancel = createString("asnCancel");

    public final QBranchMaster branchMaster;

    public final QCompanyMaster companyMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final StringPath grnCancel = createString("grnCancel");

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath isPrimary = createString("isPrimary");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath status = createString("status");

    public final QAuthUserProfile userMaster;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final QWareHouseMaster wareHouseMaster;

    public QUserWareHouse(String variable) {
        this(UserWareHouse.class, forVariable(variable), INITS);
    }

    public QUserWareHouse(Path<? extends UserWareHouse> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserWareHouse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserWareHouse(PathMetadata metadata, PathInits inits) {
        this(UserWareHouse.class, metadata, inits);
    }

    public QUserWareHouse(Class<? extends UserWareHouse> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
        this.userMaster = inits.isInitialized("userMaster") ? new QAuthUserProfile(forProperty("userMaster")) : null;
        this.wareHouseMaster = inits.isInitialized("wareHouseMaster") ? new QWareHouseMaster(forProperty("wareHouseMaster"), inits.get("wareHouseMaster")) : null;
    }

}

