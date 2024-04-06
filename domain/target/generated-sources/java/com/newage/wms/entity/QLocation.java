package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLocation is a Querydsl query type for Location
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLocation extends EntityPathBase<Location> {

    private static final long serialVersionUID = -1041779347L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLocation location = new QLocation("location");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath abc = createString("abc");

    public final QAisleMaster aisleMaster;

    public final QBranchMaster branchMaster;

    public final StringPath checkDigit = createString("checkDigit");

    public final StringPath columnCode = createString("columnCode");

    public final QCompanyMaster companyMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final StringPath deepSeq = createString("deepSeq");

    public final StringPath dimensionUnit = createString("dimensionUnit");

    public final QGroupCompanyMaster groupCompanyMaster;

    public final StringPath height = createString("height");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<Character> isBinLocation = createComparable("isBinLocation", Character.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath length = createString("length");

    public final StringPath levelCode = createString("levelCode");

    public final NumberPath<Long> levelOrder = createNumber("levelOrder", Long.class);

    public final QUomMaster locationHandlingUomMaster;

    public final NumberPath<Long> locationPathSeq = createNumber("locationPathSeq", Long.class);

    public final StringPath locationUid = createString("locationUid");

    public final QLocTypeMaster locTypeMaster;

    public final QLocation masterLocationId;

    public final ComparablePath<Character> mixedSkuAllowed = createComparable("mixedSkuAllowed", Character.class);

    public final StringPath note = createString("note");

    public final StringPath position = createString("position");

    public final QRackMaster rackMaster;

    public final ComparablePath<Character> replenishmentAllowed = createComparable("replenishmentAllowed", Character.class);

    public final StringPath status = createString("status");

    public final QStorageAreaMaster storageAreaMaster;

    public final QStorageTypeMaster storageTypeMaster;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final StringPath volume = createString("volume");

    public final QWareHouseMaster wareHouseMaster;

    public final StringPath weight = createString("weight");

    public final StringPath width = createString("width");

    public final QZoneMasterWMS zoneMaster;

    public QLocation(String variable) {
        this(Location.class, forVariable(variable), INITS);
    }

    public QLocation(Path<? extends Location> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLocation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLocation(PathMetadata metadata, PathInits inits) {
        this(Location.class, metadata, inits);
    }

    public QLocation(Class<? extends Location> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.aisleMaster = inits.isInitialized("aisleMaster") ? new QAisleMaster(forProperty("aisleMaster"), inits.get("aisleMaster")) : null;
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
        this.locationHandlingUomMaster = inits.isInitialized("locationHandlingUomMaster") ? new QUomMaster(forProperty("locationHandlingUomMaster"), inits.get("locationHandlingUomMaster")) : null;
        this.locTypeMaster = inits.isInitialized("locTypeMaster") ? new QLocTypeMaster(forProperty("locTypeMaster"), inits.get("locTypeMaster")) : null;
        this.masterLocationId = inits.isInitialized("masterLocationId") ? new QLocation(forProperty("masterLocationId"), inits.get("masterLocationId")) : null;
        this.rackMaster = inits.isInitialized("rackMaster") ? new QRackMaster(forProperty("rackMaster"), inits.get("rackMaster")) : null;
        this.storageAreaMaster = inits.isInitialized("storageAreaMaster") ? new QStorageAreaMaster(forProperty("storageAreaMaster"), inits.get("storageAreaMaster")) : null;
        this.storageTypeMaster = inits.isInitialized("storageTypeMaster") ? new QStorageTypeMaster(forProperty("storageTypeMaster"), inits.get("storageTypeMaster")) : null;
        this.wareHouseMaster = inits.isInitialized("wareHouseMaster") ? new QWareHouseMaster(forProperty("wareHouseMaster"), inits.get("wareHouseMaster")) : null;
        this.zoneMaster = inits.isInitialized("zoneMaster") ? new QZoneMasterWMS(forProperty("zoneMaster"), inits.get("zoneMaster")) : null;
    }

}

