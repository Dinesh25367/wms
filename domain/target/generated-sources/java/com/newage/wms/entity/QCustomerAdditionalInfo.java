package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerAdditionalInfo is a Querydsl query type for CustomerAdditionalInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerAdditionalInfo extends EntityPathBase<CustomerAdditionalInfo> {

    private static final long serialVersionUID = 580208811L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerAdditionalInfo customerAdditionalInfo = new QCustomerAdditionalInfo("customerAdditionalInfo");

    public final StringPath agentIATACode = createString("agentIATACode");

    public final StringPath blanket = createString("blanket");

    public final NumberPath<Long> branchId = createNumber("branchId", Long.class);

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final QCustomerMaster customer;

    public final NumberPath<Long> groupCompanyId = createNumber("groupCompanyId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath knownShipper = createString("knownShipper");

    public final StringPath panNumber = createString("panNumber");

    public final StringPath taxIdNo = createString("taxIdNo");

    public final DateTimePath<java.util.Date> tsaValidationDate = createDateTime("tsaValidationDate", java.util.Date.class);

    public final StringPath tsaValidationNumber = createString("tsaValidationNumber");

    public final StringPath tsaVerificationNumber = createString("tsaVerificationNumber");

    public final StringPath vatNumber = createString("vatNumber");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QCustomerAdditionalInfo(String variable) {
        this(CustomerAdditionalInfo.class, forVariable(variable), INITS);
    }

    public QCustomerAdditionalInfo(Path<? extends CustomerAdditionalInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerAdditionalInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerAdditionalInfo(PathMetadata metadata, PathInits inits) {
        this(CustomerAdditionalInfo.class, metadata, inits);
    }

    public QCustomerAdditionalInfo(Class<? extends CustomerAdditionalInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new QCustomerMaster(forProperty("customer"), inits.get("customer")) : null;
    }

}

