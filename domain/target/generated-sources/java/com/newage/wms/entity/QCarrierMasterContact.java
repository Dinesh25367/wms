package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCarrierMasterContact is a Querydsl query type for CarrierMasterContact
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCarrierMasterContact extends EntityPathBase<CarrierMasterContact> {

    private static final long serialVersionUID = 1982342398L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCarrierMasterContact carrierMasterContact = new QCarrierMasterContact("carrierMasterContact");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath accountNumber = createString("accountNumber");

    public final QCustomerMaster agentCode;

    public final StringPath agentEmail = createString("agentEmail");

    public final QCustomerMaster agentName;

    public final QBranchMaster branch;

    public final QCarrierMaster carrierMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public QCarrierMasterContact(String variable) {
        this(CarrierMasterContact.class, forVariable(variable), INITS);
    }

    public QCarrierMasterContact(Path<? extends CarrierMasterContact> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCarrierMasterContact(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCarrierMasterContact(PathMetadata metadata, PathInits inits) {
        this(CarrierMasterContact.class, metadata, inits);
    }

    public QCarrierMasterContact(Class<? extends CarrierMasterContact> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.agentCode = inits.isInitialized("agentCode") ? new QCustomerMaster(forProperty("agentCode"), inits.get("agentCode")) : null;
        this.agentName = inits.isInitialized("agentName") ? new QCustomerMaster(forProperty("agentName"), inits.get("agentName")) : null;
        this.branch = inits.isInitialized("branch") ? new QBranchMaster(forProperty("branch"), inits.get("branch")) : null;
        this.carrierMaster = inits.isInitialized("carrierMaster") ? new QCarrierMaster(forProperty("carrierMaster"), inits.get("carrierMaster")) : null;
    }

}

