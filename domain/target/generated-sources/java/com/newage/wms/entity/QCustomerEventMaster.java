package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerEventMaster is a Querydsl query type for CustomerEventMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerEventMaster extends EntityPathBase<CustomerEventMaster> {

    private static final long serialVersionUID = -746420698L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerEventMaster customerEventMaster = new QCustomerEventMaster("customerEventMaster");

    public final QAuditable _super = new QAuditable(this);

    public final QBranchMaster branchMaster;

    public final QCompanyMaster companyMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QCustomerMaster customer;

    public final BooleanPath deleted = createBoolean("deleted");

    public final QEventMaster event;

    public final StringPath eventEmails = createString("eventEmails");

    public final StringPath eventService = createString("eventService");

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final QServiceMaster service;

    public final StringPath tradeCode = createString("tradeCode");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QCustomerEventMaster(String variable) {
        this(CustomerEventMaster.class, forVariable(variable), INITS);
    }

    public QCustomerEventMaster(Path<? extends CustomerEventMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerEventMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerEventMaster(PathMetadata metadata, PathInits inits) {
        this(CustomerEventMaster.class, metadata, inits);
    }

    public QCustomerEventMaster(Class<? extends CustomerEventMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.customer = inits.isInitialized("customer") ? new QCustomerMaster(forProperty("customer"), inits.get("customer")) : null;
        this.event = inits.isInitialized("event") ? new QEventMaster(forProperty("event"), inits.get("event")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
        this.service = inits.isInitialized("service") ? new QServiceMaster(forProperty("service")) : null;
    }

}

