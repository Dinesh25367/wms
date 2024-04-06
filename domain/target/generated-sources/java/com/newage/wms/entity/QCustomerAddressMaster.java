package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerAddressMaster is a Querydsl query type for CustomerAddressMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerAddressMaster extends EntityPathBase<CustomerAddressMaster> {

    private static final long serialVersionUID = 644729216L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerAddressMaster customerAddressMaster = new QCustomerAddressMaster("customerAddressMaster");

    public final QAuditable _super = new QAuditable(this);

    public final QAddressTypeMaster addressType;

    public final QBranchMaster branchMaster;

    public final StringPath buildingNoName = createString("buildingNoName");

    public final QCityMaster city;

    public final BooleanPath communicationAddress = createBoolean("communicationAddress");

    public final QCompanyMaster companyMaster;

    public final StringPath contactPerson = createString("contactPerson");

    public final BooleanPath corporateAddress = createBoolean("corporateAddress");

    public final QCountryMaster country;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QCustomerMaster customer;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath email = createString("email");

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath landMark = createString("landMark");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final StringPath locationName = createString("locationName");

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath mapLink = createString("mapLink");

    public final StringPath mobileNo = createString("mobileNo");

    public final StringPath phone = createString("phone");

    public final StringPath poBoxNo = createString("poBoxNo");

    public final QStateMaster state;

    public final StringPath streetName = createString("streetName");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final BooleanPath webDomainAddress = createBoolean("webDomainAddress");

    public final StringPath zipCode = createString("zipCode");

    public QCustomerAddressMaster(String variable) {
        this(CustomerAddressMaster.class, forVariable(variable), INITS);
    }

    public QCustomerAddressMaster(Path<? extends CustomerAddressMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerAddressMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerAddressMaster(PathMetadata metadata, PathInits inits) {
        this(CustomerAddressMaster.class, metadata, inits);
    }

    public QCustomerAddressMaster(Class<? extends CustomerAddressMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.addressType = inits.isInitialized("addressType") ? new QAddressTypeMaster(forProperty("addressType")) : null;
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.city = inits.isInitialized("city") ? new QCityMaster(forProperty("city"), inits.get("city")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.country = inits.isInitialized("country") ? new QCountryMaster(forProperty("country"), inits.get("country")) : null;
        this.customer = inits.isInitialized("customer") ? new QCustomerMaster(forProperty("customer"), inits.get("customer")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
        this.state = inits.isInitialized("state") ? new QStateMaster(forProperty("state"), inits.get("state")) : null;
    }

}

