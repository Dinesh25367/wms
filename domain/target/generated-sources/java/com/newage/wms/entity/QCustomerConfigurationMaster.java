package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerConfigurationMaster is a Querydsl query type for CustomerConfigurationMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerConfigurationMaster extends EntityPathBase<CustomerConfigurationMaster> {

    private static final long serialVersionUID = -571528830L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerConfigurationMaster customerConfigurationMaster = new QCustomerConfigurationMaster("customerConfigurationMaster");

    public final QAuditable _super = new QAuditable(this);

    public final QBranchMaster branchMaster;

    public final QCompanyMaster companyMaster;

    public final StringPath configurationFlag = createString("configurationFlag");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QCustomerMaster customerMaster;

    public final StringPath dataType = createString("dataType");

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath isMandatory = createString("isMandatory");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath module = createString("module");

    public final StringPath note = createString("note");

    public final StringPath value = createString("value");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QCustomerConfigurationMaster(String variable) {
        this(CustomerConfigurationMaster.class, forVariable(variable), INITS);
    }

    public QCustomerConfigurationMaster(Path<? extends CustomerConfigurationMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerConfigurationMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerConfigurationMaster(PathMetadata metadata, PathInits inits) {
        this(CustomerConfigurationMaster.class, metadata, inits);
    }

    public QCustomerConfigurationMaster(Class<? extends CustomerConfigurationMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.customerMaster = inits.isInitialized("customerMaster") ? new QCustomerMaster(forProperty("customerMaster"), inits.get("customerMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
    }

}

