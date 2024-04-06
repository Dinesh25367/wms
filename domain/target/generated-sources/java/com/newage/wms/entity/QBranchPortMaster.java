package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBranchPortMaster is a Querydsl query type for BranchPortMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBranchPortMaster extends EntityPathBase<BranchPortMaster> {

    private static final long serialVersionUID = -1333049539L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBranchPortMaster branchPortMaster = new QBranchPortMaster("branchPortMaster");

    public final QAuditable _super = new QAuditable(this);

    public final QBranchMaster branchMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final QPortMaster portMaster;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QBranchPortMaster(String variable) {
        this(BranchPortMaster.class, forVariable(variable), INITS);
    }

    public QBranchPortMaster(Path<? extends BranchPortMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBranchPortMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBranchPortMaster(PathMetadata metadata, PathInits inits) {
        this(BranchPortMaster.class, metadata, inits);
    }

    public QBranchPortMaster(Class<? extends BranchPortMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.portMaster = inits.isInitialized("portMaster") ? new QPortMaster(forProperty("portMaster"), inits.get("portMaster")) : null;
    }

}

