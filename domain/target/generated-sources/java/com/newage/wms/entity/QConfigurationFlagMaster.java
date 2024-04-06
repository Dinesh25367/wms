package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConfigurationFlagMaster is a Querydsl query type for ConfigurationFlagMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConfigurationFlagMaster extends EntityPathBase<ConfigurationFlagMaster> {

    private static final long serialVersionUID = 260222412L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConfigurationFlagMaster configurationFlagMaster = new QConfigurationFlagMaster("configurationFlagMaster");

    public final QAuditable _super = new QAuditable(this);

    public final QAuthUserProfile authUserProfile;

    public final QBranchMaster branchMaster;

    public final StringPath code = createString("code");

    public final QCompanyMaster companyMaster;

    public final StringPath configurationFlag = createString("configurationFlag");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final StringPath description = createString("description");

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath screen = createString("screen");

    public final StringPath status = createString("status");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QConfigurationFlagMaster(String variable) {
        this(ConfigurationFlagMaster.class, forVariable(variable), INITS);
    }

    public QConfigurationFlagMaster(Path<? extends ConfigurationFlagMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConfigurationFlagMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConfigurationFlagMaster(PathMetadata metadata, PathInits inits) {
        this(ConfigurationFlagMaster.class, metadata, inits);
    }

    public QConfigurationFlagMaster(Class<? extends ConfigurationFlagMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.authUserProfile = inits.isInitialized("authUserProfile") ? new QAuthUserProfile(forProperty("authUserProfile")) : null;
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
    }

}

