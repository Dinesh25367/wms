package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserCustomer is a Querydsl query type for UserCustomer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserCustomer extends EntityPathBase<UserCustomer> {

    private static final long serialVersionUID = 1828867713L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserCustomer userCustomer = new QUserCustomer("userCustomer");

    public final QAuditable _super = new QAuditable(this);

    public final QBranchMaster branchMaster;

    public final QCompanyMaster companyMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QCustomerMaster customerMaster;

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath isPrimary = createString("isPrimary");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath status = createString("status");

    public final QAuthUserProfile userMaster;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QUserCustomer(String variable) {
        this(UserCustomer.class, forVariable(variable), INITS);
    }

    public QUserCustomer(Path<? extends UserCustomer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserCustomer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserCustomer(PathMetadata metadata, PathInits inits) {
        this(UserCustomer.class, metadata, inits);
    }

    public QUserCustomer(Class<? extends UserCustomer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.customerMaster = inits.isInitialized("customerMaster") ? new QCustomerMaster(forProperty("customerMaster"), inits.get("customerMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
        this.userMaster = inits.isInitialized("userMaster") ? new QAuthUserProfile(forProperty("userMaster")) : null;
    }

}

