package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLocTypeMaster is a Querydsl query type for LocTypeMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLocTypeMaster extends EntityPathBase<LocTypeMaster> {

    private static final long serialVersionUID = -22806812L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLocTypeMaster locTypeMaster = new QLocTypeMaster("locTypeMaster");

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

    public final StringPath type = createString("type");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QLocTypeMaster(String variable) {
        this(LocTypeMaster.class, forVariable(variable), INITS);
    }

    public QLocTypeMaster(Path<? extends LocTypeMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLocTypeMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLocTypeMaster(PathMetadata metadata, PathInits inits) {
        this(LocTypeMaster.class, metadata, inits);
    }

    public QLocTypeMaster(Class<? extends LocTypeMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
    }

}

