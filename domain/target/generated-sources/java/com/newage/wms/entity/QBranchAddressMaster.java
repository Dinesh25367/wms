package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBranchAddressMaster is a Querydsl query type for BranchAddressMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBranchAddressMaster extends EntityPathBase<BranchAddressMaster> {

    private static final long serialVersionUID = 906974268L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBranchAddressMaster branchAddressMaster = new QBranchAddressMaster("branchAddressMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath addressLine1 = createString("addressLine1");

    public final StringPath addressLine2 = createString("addressLine2");

    public final StringPath addressLine3 = createString("addressLine3");

    public final QBranchMaster branchMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final StringPath fax = createString("fax");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath mobileNo = createString("mobileNo");

    public final StringPath phoneNo = createString("phoneNo");

    public final BooleanPath primaryAddress = createBoolean("primaryAddress");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final StringPath zipCode = createString("zipCode");

    public QBranchAddressMaster(String variable) {
        this(BranchAddressMaster.class, forVariable(variable), INITS);
    }

    public QBranchAddressMaster(Path<? extends BranchAddressMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBranchAddressMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBranchAddressMaster(PathMetadata metadata, PathInits inits) {
        this(BranchAddressMaster.class, metadata, inits);
    }

    public QBranchAddressMaster(Class<? extends BranchAddressMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
    }

}

