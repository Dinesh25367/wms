package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerMaster is a Querydsl query type for CustomerMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerMaster extends EntityPathBase<CustomerMaster> {

    private static final long serialVersionUID = -833707112L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerMaster customerMaster = new QCustomerMaster("customerMaster");

    public final QAuditable _super = new QAuditable(this);

    public final QBranchMaster branchMaster;

    public final StringPath businessRelationStatus = createString("businessRelationStatus");

    public final StringPath businessRelationStatusNote = createString("businessRelationStatusNote");

    public final StringPath code = createString("code");

    public final QCompanyMaster companyMaster;

    public final QCountryMaster country;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final ListPath<CustomerAddressMaster, QCustomerAddressMaster> customerAddressMasterList = this.<CustomerAddressMaster, QCustomerAddressMaster>createList("customerAddressMasterList", CustomerAddressMaster.class, QCustomerAddressMaster.class, PathInits.DIRECT2);

    public final ListPath<CustomerContactMaster, QCustomerContactMaster> customerContactMasterList = this.<CustomerContactMaster, QCustomerContactMaster>createList("customerContactMasterList", CustomerContactMaster.class, QCustomerContactMaster.class, PathInits.DIRECT2);

    public final ListPath<CustomerMasterEmployee, QCustomerMasterEmployee> customerEmployees = this.<CustomerMasterEmployee, QCustomerMasterEmployee>createList("customerEmployees", CustomerMasterEmployee.class, QCustomerMasterEmployee.class, PathInits.DIRECT2);

    public final ListPath<CustomerEventMaster, QCustomerEventMaster> customerEventMasterList = this.<CustomerEventMaster, QCustomerEventMaster>createList("customerEventMasterList", CustomerEventMaster.class, QCustomerEventMaster.class, PathInits.DIRECT2);

    public final ListPath<CustomerProductMaster, QCustomerProductMaster> customerProductMasterList = this.<CustomerProductMaster, QCustomerProductMaster>createList("customerProductMasterList", CustomerProductMaster.class, QCustomerProductMaster.class, PathInits.DIRECT2);

    public final StringPath customerStatus = createString("customerStatus");

    public final StringPath customerTerm = createString("customerTerm");

    public final ListPath<CustomerTypeCustomerMaster, QCustomerTypeCustomerMaster> customerType = this.<CustomerTypeCustomerMaster, QCustomerTypeCustomerMaster>createList("customerType", CustomerTypeCustomerMaster.class, QCustomerTypeCustomerMaster.class, PathInits.DIRECT2);

    public final BooleanPath deleted = createBoolean("deleted");

    public final QGradeMaster grade;

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final BooleanPath overseasAgent = createBoolean("overseasAgent");

    public final StringPath searchKey = createString("searchKey");

    public final StringPath shortName = createString("shortName");

    public final StringPath status = createString("status");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QCustomerMaster(String variable) {
        this(CustomerMaster.class, forVariable(variable), INITS);
    }

    public QCustomerMaster(Path<? extends CustomerMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerMaster(PathMetadata metadata, PathInits inits) {
        this(CustomerMaster.class, metadata, inits);
    }

    public QCustomerMaster(Class<? extends CustomerMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.country = inits.isInitialized("country") ? new QCountryMaster(forProperty("country"), inits.get("country")) : null;
        this.grade = inits.isInitialized("grade") ? new QGradeMaster(forProperty("grade")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
    }

}

