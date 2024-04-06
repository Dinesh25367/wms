package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerTypeCustomerMaster is a Querydsl query type for CustomerTypeCustomerMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerTypeCustomerMaster extends EntityPathBase<CustomerTypeCustomerMaster> {

    private static final long serialVersionUID = -1268498960L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerTypeCustomerMaster customerTypeCustomerMaster = new QCustomerTypeCustomerMaster("customerTypeCustomerMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QCustomerMaster customer;

    public final QCustomerTypeMaster customerType;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QCustomerTypeCustomerMaster(String variable) {
        this(CustomerTypeCustomerMaster.class, forVariable(variable), INITS);
    }

    public QCustomerTypeCustomerMaster(Path<? extends CustomerTypeCustomerMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerTypeCustomerMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerTypeCustomerMaster(PathMetadata metadata, PathInits inits) {
        this(CustomerTypeCustomerMaster.class, metadata, inits);
    }

    public QCustomerTypeCustomerMaster(Class<? extends CustomerTypeCustomerMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new QCustomerMaster(forProperty("customer"), inits.get("customer")) : null;
        this.customerType = inits.isInitialized("customerType") ? new QCustomerTypeMaster(forProperty("customerType")) : null;
    }

}

