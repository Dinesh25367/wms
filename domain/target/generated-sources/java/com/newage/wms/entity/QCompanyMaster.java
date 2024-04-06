package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompanyMaster is a Querydsl query type for CompanyMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCompanyMaster extends EntityPathBase<CompanyMaster> {

    private static final long serialVersionUID = -1865417081L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompanyMaster companyMaster = new QCompanyMaster("companyMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath code = createString("code");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath endMonth = createString("endMonth");

    public final StringPath financialYear = createString("financialYear");

    public final QGroupCompanyMaster groupCompany;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final ArrayPath<byte[], Byte> logo = createArray("logo", byte[].class);

    public final StringPath name = createString("name");

    public final StringPath reportingName = createString("reportingName");

    public final StringPath startMonth = createString("startMonth");

    public final StringPath status = createString("status");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QCompanyMaster(String variable) {
        this(CompanyMaster.class, forVariable(variable), INITS);
    }

    public QCompanyMaster(Path<? extends CompanyMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompanyMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompanyMaster(PathMetadata metadata, PathInits inits) {
        this(CompanyMaster.class, metadata, inits);
    }

    public QCompanyMaster(Class<? extends CompanyMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.groupCompany = inits.isInitialized("groupCompany") ? new QGroupCompanyMaster(forProperty("groupCompany")) : null;
    }

}

