package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStorageTypeMaster is a Querydsl query type for StorageTypeMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStorageTypeMaster extends EntityPathBase<StorageTypeMaster> {

    private static final long serialVersionUID = -1076024225L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStorageTypeMaster storageTypeMaster = new QStorageTypeMaster("storageTypeMaster");

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

    public final StringPath note = createString("note");

    public final StringPath status = createString("status");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QStorageTypeMaster(String variable) {
        this(StorageTypeMaster.class, forVariable(variable), INITS);
    }

    public QStorageTypeMaster(Path<? extends StorageTypeMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStorageTypeMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStorageTypeMaster(PathMetadata metadata, PathInits inits) {
        this(StorageTypeMaster.class, metadata, inits);
    }

    public QStorageTypeMaster(Class<? extends StorageTypeMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
    }

}

