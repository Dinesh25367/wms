package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerEmployeeService is a Querydsl query type for CustomerEmployeeService
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerEmployeeService extends EntityPathBase<CustomerEmployeeService> {

    private static final long serialVersionUID = -909395023L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerEmployeeService customerEmployeeService = new QCustomerEmployeeService("customerEmployeeService");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QCustomerMasterEmployee customerMasterEmployee;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final QServiceMaster service;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QCustomerEmployeeService(String variable) {
        this(CustomerEmployeeService.class, forVariable(variable), INITS);
    }

    public QCustomerEmployeeService(Path<? extends CustomerEmployeeService> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerEmployeeService(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerEmployeeService(PathMetadata metadata, PathInits inits) {
        this(CustomerEmployeeService.class, metadata, inits);
    }

    public QCustomerEmployeeService(Class<? extends CustomerEmployeeService> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customerMasterEmployee = inits.isInitialized("customerMasterEmployee") ? new QCustomerMasterEmployee(forProperty("customerMasterEmployee"), inits.get("customerMasterEmployee")) : null;
        this.service = inits.isInitialized("service") ? new QServiceMaster(forProperty("service")) : null;
    }

}

