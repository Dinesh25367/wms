package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGrnHeader is a Querydsl query type for GrnHeader
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGrnHeader extends EntityPathBase<GrnHeader> {

    private static final long serialVersionUID = -406812008L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGrnHeader grnHeader = new QGrnHeader("grnHeader");

    public final QAuditable _super = new QAuditable(this);

    public final QBranchMaster branchMaster;

    public final QCompanyMaster companyMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final DateTimePath<java.util.Date> grnDate = createDateTime("grnDate", java.util.Date.class);

    public final ListPath<GrnDetail, QGrnDetail> grnDetailList = this.<GrnDetail, QGrnDetail>createList("grnDetailList", GrnDetail.class, QGrnDetail.class, PathInits.DIRECT2);

    public final StringPath grnRef = createString("grnRef");

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath status = createString("status");

    public final StringPath transactionUid = createString("transactionUid");

    public final QTrnHeaderAsn trnHeaderAsnMaster;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QGrnHeader(String variable) {
        this(GrnHeader.class, forVariable(variable), INITS);
    }

    public QGrnHeader(Path<? extends GrnHeader> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGrnHeader(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGrnHeader(PathMetadata metadata, PathInits inits) {
        this(GrnHeader.class, metadata, inits);
    }

    public QGrnHeader(Class<? extends GrnHeader> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
        this.trnHeaderAsnMaster = inits.isInitialized("trnHeaderAsnMaster") ? new QTrnHeaderAsn(forProperty("trnHeaderAsnMaster"), inits.get("trnHeaderAsnMaster")) : null;
    }

}

