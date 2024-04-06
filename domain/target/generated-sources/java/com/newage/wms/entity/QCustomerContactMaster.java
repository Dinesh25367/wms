package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerContactMaster is a Querydsl query type for CustomerContactMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerContactMaster extends EntityPathBase<CustomerContactMaster> {

    private static final long serialVersionUID = 2002354348L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerContactMaster customerContactMaster = new QCustomerContactMaster("customerContactMaster");

    public final QAuditable _super = new QAuditable(this);

    public final QBranchMaster branchMaster;

    public final QCityMaster city;

    public final QCompanyMaster companyMaster;

    public final StringPath contactSalutation = createString("contactSalutation");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QCustomerMaster customer;

    public final BooleanPath deleted = createBoolean("deleted");

    public final QDepartmentMaster department;

    public final QDesignationMaster designation;

    public final StringPath email = createString("email");

    public final StringPath firstName = createString("firstName");

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath lastName = createString("lastName");

    public final StringPath mobileNo = createString("mobileNo");

    public final StringPath officePhone = createString("officePhone");

    public final StringPath preferredContactMethod = createString("preferredContactMethod");

    public final StringPath preMobileNo = createString("preMobileNo");

    public final StringPath preOfficeNo = createString("preOfficeNo");

    public final BooleanPath primary = createBoolean("primary");

    public final NumberPath<Integer> serialNo = createNumber("serialNo", Integer.class);

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QCustomerContactMaster(String variable) {
        this(CustomerContactMaster.class, forVariable(variable), INITS);
    }

    public QCustomerContactMaster(Path<? extends CustomerContactMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerContactMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerContactMaster(PathMetadata metadata, PathInits inits) {
        this(CustomerContactMaster.class, metadata, inits);
    }

    public QCustomerContactMaster(Class<? extends CustomerContactMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.city = inits.isInitialized("city") ? new QCityMaster(forProperty("city"), inits.get("city")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.customer = inits.isInitialized("customer") ? new QCustomerMaster(forProperty("customer"), inits.get("customer")) : null;
        this.department = inits.isInitialized("department") ? new QDepartmentMaster(forProperty("department")) : null;
        this.designation = inits.isInitialized("designation") ? new QDesignationMaster(forProperty("designation"), inits.get("designation")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
    }

}

