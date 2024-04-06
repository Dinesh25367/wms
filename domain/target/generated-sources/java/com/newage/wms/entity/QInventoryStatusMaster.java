package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInventoryStatusMaster is a Querydsl query type for InventoryStatusMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QInventoryStatusMaster extends EntityPathBase<InventoryStatusMaster> {

    private static final long serialVersionUID = -1661214280L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInventoryStatusMaster inventoryStatusMaster = new QInventoryStatusMaster("inventoryStatusMaster");

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

    public final StringPath isSaleable = createString("isSaleable");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QInventoryStatusMaster(String variable) {
        this(InventoryStatusMaster.class, forVariable(variable), INITS);
    }

    public QInventoryStatusMaster(Path<? extends InventoryStatusMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInventoryStatusMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInventoryStatusMaster(PathMetadata metadata, PathInits inits) {
        this(InventoryStatusMaster.class, metadata, inits);
    }

    public QInventoryStatusMaster(Class<? extends InventoryStatusMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
    }

}

