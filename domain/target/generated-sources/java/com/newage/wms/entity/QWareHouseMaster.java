package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWareHouseMaster is a Querydsl query type for WareHouseMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWareHouseMaster extends EntityPathBase<WareHouseMaster> {

    private static final long serialVersionUID = -1312479699L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWareHouseMaster wareHouseMaster = new QWareHouseMaster("wareHouseMaster");

    public final QAuditable _super = new QAuditable(this);

    public final QBranchMaster branchMaster;

    public final StringPath code = createString("code");

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QLocation crossDockLocationMaster;

    public final NumberPath<Long> groupCompanyId = createNumber("groupCompanyId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<Character> isBonded = createComparable("isBonded", Character.class);

    public final ComparablePath<Character> isThirdPartyWareHouse = createComparable("isThirdPartyWareHouse", Character.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final DateTimePath<java.util.Date> openDate = createDateTime("openDate", java.util.Date.class);

    public final QPartyAddressDetail partyAddressDetail;

    public final StringPath status = createString("status");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final QWareHouseContactDetail wareHouseContactDetail;

    public final StringPath wareHouseId = createString("wareHouseId");

    public final StringPath wareHouseLocationPrefix = createString("wareHouseLocationPrefix");

    public QWareHouseMaster(String variable) {
        this(WareHouseMaster.class, forVariable(variable), INITS);
    }

    public QWareHouseMaster(Path<? extends WareHouseMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWareHouseMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWareHouseMaster(PathMetadata metadata, PathInits inits) {
        this(WareHouseMaster.class, metadata, inits);
    }

    public QWareHouseMaster(Class<? extends WareHouseMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.crossDockLocationMaster = inits.isInitialized("crossDockLocationMaster") ? new QLocation(forProperty("crossDockLocationMaster"), inits.get("crossDockLocationMaster")) : null;
        this.partyAddressDetail = inits.isInitialized("partyAddressDetail") ? new QPartyAddressDetail(forProperty("partyAddressDetail"), inits.get("partyAddressDetail")) : null;
        this.wareHouseContactDetail = inits.isInitialized("wareHouseContactDetail") ? new QWareHouseContactDetail(forProperty("wareHouseContactDetail"), inits.get("wareHouseContactDetail")) : null;
    }

}

