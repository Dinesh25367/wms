package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerProductMaster is a Querydsl query type for CustomerProductMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerProductMaster extends EntityPathBase<CustomerProductMaster> {

    private static final long serialVersionUID = -1866722757L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerProductMaster customerProductMaster = new QCustomerProductMaster("customerProductMaster");

    public final QAuditable _super = new QAuditable(this);

    public final BooleanPath allDestinationPort = createBoolean("allDestinationPort");

    public final BooleanPath allOriginPort = createBoolean("allOriginPort");

    public final BooleanPath allService = createBoolean("allService");

    public final BooleanPath allTos = createBoolean("allTos");

    public final QBranchMaster branchMaster;

    public final QCompanyMaster companyMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QCustomerMaster customer;

    public final BooleanPath deleted = createBoolean("deleted");

    public final QPortMaster destination;

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Integer> noOfShipment = createNumber("noOfShipment", Integer.class);

    public final QPortMaster origin;

    public final NumberPath<Integer> serialNo = createNumber("serialNo", Integer.class);

    public final QServiceMaster service;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QCustomerProductMaster(String variable) {
        this(CustomerProductMaster.class, forVariable(variable), INITS);
    }

    public QCustomerProductMaster(Path<? extends CustomerProductMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerProductMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerProductMaster(PathMetadata metadata, PathInits inits) {
        this(CustomerProductMaster.class, metadata, inits);
    }

    public QCustomerProductMaster(Class<? extends CustomerProductMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.customer = inits.isInitialized("customer") ? new QCustomerMaster(forProperty("customer"), inits.get("customer")) : null;
        this.destination = inits.isInitialized("destination") ? new QPortMaster(forProperty("destination"), inits.get("destination")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
        this.origin = inits.isInitialized("origin") ? new QPortMaster(forProperty("origin"), inits.get("origin")) : null;
        this.service = inits.isInitialized("service") ? new QServiceMaster(forProperty("service")) : null;
    }

}

