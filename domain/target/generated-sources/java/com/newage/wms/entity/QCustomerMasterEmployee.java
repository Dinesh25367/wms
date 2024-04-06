package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerMasterEmployee is a Querydsl query type for CustomerMasterEmployee
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerMasterEmployee extends EntityPathBase<CustomerMasterEmployee> {

    private static final long serialVersionUID = -2036672922L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerMasterEmployee customerMasterEmployee = new QCustomerMasterEmployee("customerMasterEmployee");

    public final QAuditable _super = new QAuditable(this);

    public final BooleanPath allService = createBoolean("allService");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QCustomerMaster customer;

    public final BooleanPath deleted = createBoolean("deleted");

    public final QDepartmentMaster department;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final ListPath<CustomerEmployeeService, QCustomerEmployeeService> service = this.<CustomerEmployeeService, QCustomerEmployeeService>createList("service", CustomerEmployeeService.class, QCustomerEmployeeService.class, PathInits.DIRECT2);

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QCustomerMasterEmployee(String variable) {
        this(CustomerMasterEmployee.class, forVariable(variable), INITS);
    }

    public QCustomerMasterEmployee(Path<? extends CustomerMasterEmployee> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerMasterEmployee(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerMasterEmployee(PathMetadata metadata, PathInits inits) {
        this(CustomerMasterEmployee.class, metadata, inits);
    }

    public QCustomerMasterEmployee(Class<? extends CustomerMasterEmployee> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new QCustomerMaster(forProperty("customer"), inits.get("customer")) : null;
        this.department = inits.isInitialized("department") ? new QDepartmentMaster(forProperty("department")) : null;
    }

}

