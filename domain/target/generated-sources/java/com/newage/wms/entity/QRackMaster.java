package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRackMaster is a Querydsl query type for RackMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRackMaster extends EntityPathBase<RackMaster> {

    private static final long serialVersionUID = 1389177809L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRackMaster rackMaster = new QRackMaster("rackMaster");

    public final QAuditable _super = new QAuditable(this);

    public final QAisleMaster aisleMaster;

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

    public final StringPath note = createString("note");

    public final StringPath side = createString("side");

    public final StringPath status = createString("status");

    public final QStorageAreaMaster storageAreaMaster;

    public final QStorageTypeMaster storageTypeMaster;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final QWareHouseMaster wareHouseMaster;

    public QRackMaster(String variable) {
        this(RackMaster.class, forVariable(variable), INITS);
    }

    public QRackMaster(Path<? extends RackMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRackMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRackMaster(PathMetadata metadata, PathInits inits) {
        this(RackMaster.class, metadata, inits);
    }

    public QRackMaster(Class<? extends RackMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.aisleMaster = inits.isInitialized("aisleMaster") ? new QAisleMaster(forProperty("aisleMaster"), inits.get("aisleMaster")) : null;
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
        this.storageAreaMaster = inits.isInitialized("storageAreaMaster") ? new QStorageAreaMaster(forProperty("storageAreaMaster"), inits.get("storageAreaMaster")) : null;
        this.storageTypeMaster = inits.isInitialized("storageTypeMaster") ? new QStorageTypeMaster(forProperty("storageTypeMaster"), inits.get("storageTypeMaster")) : null;
        this.wareHouseMaster = inits.isInitialized("wareHouseMaster") ? new QWareHouseMaster(forProperty("wareHouseMaster"), inits.get("wareHouseMaster")) : null;
    }

}

