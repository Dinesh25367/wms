package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QZoneMasterWMS is a Querydsl query type for ZoneMasterWMS
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QZoneMasterWMS extends EntityPathBase<ZoneMasterWMS> {

    private static final long serialVersionUID = 1038716311L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QZoneMasterWMS zoneMasterWMS = new QZoneMasterWMS("zoneMasterWMS");

    public final QAuditable _super = new QAuditable(this);

    public final QBranchMaster branchMaster;

    public final StringPath code = createString("code");

    public final QCompanyMaster companyMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final StringPath defaultZone = createString("defaultZone");

    public final QDoorMaster doorMaster;

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLocation inLocationMaster;

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final QLocation outLocationMaster;

    public final StringPath status = createString("status");

    public final QStorageAreaMaster storageAreaMaster;

    public final QStorageTypeMaster storageTypeMaster;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final QWareHouseMaster wareHouseMaster;

    public QZoneMasterWMS(String variable) {
        this(ZoneMasterWMS.class, forVariable(variable), INITS);
    }

    public QZoneMasterWMS(Path<? extends ZoneMasterWMS> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QZoneMasterWMS(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QZoneMasterWMS(PathMetadata metadata, PathInits inits) {
        this(ZoneMasterWMS.class, metadata, inits);
    }

    public QZoneMasterWMS(Class<? extends ZoneMasterWMS> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.doorMaster = inits.isInitialized("doorMaster") ? new QDoorMaster(forProperty("doorMaster"), inits.get("doorMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
        this.inLocationMaster = inits.isInitialized("inLocationMaster") ? new QLocation(forProperty("inLocationMaster"), inits.get("inLocationMaster")) : null;
        this.outLocationMaster = inits.isInitialized("outLocationMaster") ? new QLocation(forProperty("outLocationMaster"), inits.get("outLocationMaster")) : null;
        this.storageAreaMaster = inits.isInitialized("storageAreaMaster") ? new QStorageAreaMaster(forProperty("storageAreaMaster"), inits.get("storageAreaMaster")) : null;
        this.storageTypeMaster = inits.isInitialized("storageTypeMaster") ? new QStorageTypeMaster(forProperty("storageTypeMaster"), inits.get("storageTypeMaster")) : null;
        this.wareHouseMaster = inits.isInitialized("wareHouseMaster") ? new QWareHouseMaster(forProperty("wareHouseMaster"), inits.get("wareHouseMaster")) : null;
    }

}

