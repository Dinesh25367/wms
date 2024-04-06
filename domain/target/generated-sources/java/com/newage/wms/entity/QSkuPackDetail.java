package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSkuPackDetail is a Querydsl query type for SkuPackDetail
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSkuPackDetail extends EntityPathBase<SkuPackDetail> {

    private static final long serialVersionUID = 231343631L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSkuPackDetail skuPackDetail = new QSkuPackDetail("skuPackDetail");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath actual = createString("actual");

    public final QBranchMaster branchMaster;

    public final QCompanyMaster companyMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QGroupCompanyMaster groupCompanyMaster;

    public final StringPath gw = createString("gw");

    public final StringPath height = createString("height");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath length = createString("length");

    public final StringPath nw = createString("nw");

    public final StringPath ratio = createString("ratio");

    public final StringPath unit = createString("unit");

    public final QUomMaster uomMaster;

    public final StringPath uomType = createString("uomType");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final StringPath vol = createString("vol");

    public final StringPath width = createString("width");

    public QSkuPackDetail(String variable) {
        this(SkuPackDetail.class, forVariable(variable), INITS);
    }

    public QSkuPackDetail(Path<? extends SkuPackDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSkuPackDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSkuPackDetail(PathMetadata metadata, PathInits inits) {
        this(SkuPackDetail.class, metadata, inits);
    }

    public QSkuPackDetail(Class<? extends SkuPackDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
        this.uomMaster = inits.isInitialized("uomMaster") ? new QUomMaster(forProperty("uomMaster"), inits.get("uomMaster")) : null;
    }

}

