package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBranchMaster is a Querydsl query type for BranchMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBranchMaster extends EntityPathBase<BranchMaster> {

    private static final long serialVersionUID = 776565852L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBranchMaster branchMaster = new QBranchMaster("branchMaster");

    public final QAuditable _super = new QAuditable(this);

    public final QCustomerMaster agentMaster;

    public final ListPath<BranchAddressMaster, QBranchAddressMaster> branchAddressMasterList = this.<BranchAddressMaster, QBranchAddressMaster>createList("branchAddressMasterList", BranchAddressMaster.class, QBranchAddressMaster.class, PathInits.DIRECT2);

    public final QCityMaster cityMaster;

    public final StringPath code = createString("code");

    public final QCompanyMaster companyMaster;

    public final QCountryMaster countryMaster;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QCurrencyMaster currencyMaster;

    public final StringPath dateFormat = createString("dateFormat");

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath gstNumber = createString("gstNumber");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final ArrayPath<byte[], Byte> logo = createArray("logo", byte[].class);

    public final StringPath name = createString("name");

    public final ListPath<BranchPortMaster, QBranchPortMaster> portMaster = this.<BranchPortMaster, QBranchPortMaster>createList("portMaster", BranchPortMaster.class, QBranchPortMaster.class, PathInits.DIRECT2);

    public final StringPath reportingName = createString("reportingName");

    public final QStateMaster stateMaster;

    public final StringPath status = createString("status");

    public final QTimeZoneMaster timeZoneMaster;

    public final StringPath transportName = createString("transportName");

    public final StringPath vatNumber = createString("vatNumber");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final QZoneMaster zoneMaster;

    public QBranchMaster(String variable) {
        this(BranchMaster.class, forVariable(variable), INITS);
    }

    public QBranchMaster(Path<? extends BranchMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBranchMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBranchMaster(PathMetadata metadata, PathInits inits) {
        this(BranchMaster.class, metadata, inits);
    }

    public QBranchMaster(Class<? extends BranchMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.agentMaster = inits.isInitialized("agentMaster") ? new QCustomerMaster(forProperty("agentMaster"), inits.get("agentMaster")) : null;
        this.cityMaster = inits.isInitialized("cityMaster") ? new QCityMaster(forProperty("cityMaster"), inits.get("cityMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.countryMaster = inits.isInitialized("countryMaster") ? new QCountryMaster(forProperty("countryMaster"), inits.get("countryMaster")) : null;
        this.currencyMaster = inits.isInitialized("currencyMaster") ? new QCurrencyMaster(forProperty("currencyMaster"), inits.get("currencyMaster")) : null;
        this.stateMaster = inits.isInitialized("stateMaster") ? new QStateMaster(forProperty("stateMaster"), inits.get("stateMaster")) : null;
        this.timeZoneMaster = inits.isInitialized("timeZoneMaster") ? new QTimeZoneMaster(forProperty("timeZoneMaster")) : null;
        this.zoneMaster = inits.isInitialized("zoneMaster") ? new QZoneMaster(forProperty("zoneMaster"), inits.get("zoneMaster")) : null;
    }

}

