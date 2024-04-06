package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUomMaster is a Querydsl query type for UomMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUomMaster extends EntityPathBase<UomMaster> {

    private static final long serialVersionUID = -1622334819L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUomMaster uomMaster = new QUomMaster("uomMaster");

    public final QAuditable _super = new QAuditable(this);

    public final QBranchMaster branchMaster;

    public final QCategoryMaster categoryMaster;

    public final StringPath code = createString("code");

    public final QCompanyMaster companyMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final NumberPath<Long> decimalPlaces = createNumber("decimalPlaces", Long.class);

    public final StringPath description = createString("description");

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final NumberPath<Double> ratio = createNumber("ratio", Double.class);

    public final StringPath reference = createString("reference");

    public final StringPath restrictionOfUom = createString("restrictionOfUom");

    public final StringPath status = createString("status");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QUomMaster(String variable) {
        this(UomMaster.class, forVariable(variable), INITS);
    }

    public QUomMaster(Path<? extends UomMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUomMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUomMaster(PathMetadata metadata, PathInits inits) {
        this(UomMaster.class, metadata, inits);
    }

    public QUomMaster(Class<? extends UomMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.categoryMaster = inits.isInitialized("categoryMaster") ? new QCategoryMaster(forProperty("categoryMaster"), inits.get("categoryMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
    }

}

