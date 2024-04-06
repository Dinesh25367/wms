package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPutAwayTaskHeader is a Querydsl query type for PutAwayTaskHeader
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPutAwayTaskHeader extends EntityPathBase<PutAwayTaskHeader> {

    private static final long serialVersionUID = -1367094217L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPutAwayTaskHeader putAwayTaskHeader = new QPutAwayTaskHeader("putAwayTaskHeader");

    public final QAuditable _super = new QAuditable(this);

    public final QBranchMaster branchMaster;

    public final QCompanyMaster companyMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QGrnHeader grnHeader;

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath note = createString("note");

    public final ListPath<PutAwayTaskDetails, QPutAwayTaskDetails> putAwayTaskDetailsList = this.<PutAwayTaskDetails, QPutAwayTaskDetails>createList("putAwayTaskDetailsList", PutAwayTaskDetails.class, QPutAwayTaskDetails.class, PathInits.DIRECT2);

    public final StringPath taskId = createString("taskId");

    public final StringPath taskStatus = createString("taskStatus");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QPutAwayTaskHeader(String variable) {
        this(PutAwayTaskHeader.class, forVariable(variable), INITS);
    }

    public QPutAwayTaskHeader(Path<? extends PutAwayTaskHeader> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPutAwayTaskHeader(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPutAwayTaskHeader(PathMetadata metadata, PathInits inits) {
        this(PutAwayTaskHeader.class, metadata, inits);
    }

    public QPutAwayTaskHeader(Class<? extends PutAwayTaskHeader> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.grnHeader = inits.isInitialized("grnHeader") ? new QGrnHeader(forProperty("grnHeader"), inits.get("grnHeader")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
    }

}

