package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConfigurationMaster is a Querydsl query type for ConfigurationMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConfigurationMaster extends EntityPathBase<ConfigurationMaster> {

    private static final long serialVersionUID = -1398595008L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConfigurationMaster configurationMaster = new QConfigurationMaster("configurationMaster");

    public final QAuditable _super = new QAuditable(this);

    public final QAuthUserProfile authUserProfile;

    public final QBranchMaster branchMaster;

    public final StringPath cancelRights = createString("cancelRights");

    public final QCompanyMaster companyMaster;

    public final StringPath configurationFlag = createString("configurationFlag");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final StringPath dataType = createString("dataType");

    public final StringPath description = createString("description");

    public final QGroupCompanyMaster groupCompanyMaster;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath module = createString("module");

    public final StringPath note = createString("note");

    public final StringPath screen = createString("screen");

    public final StringPath status = createString("status");

    public final StringPath tab = createString("tab");

    public final StringPath value = createString("value");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QConfigurationMaster(String variable) {
        this(ConfigurationMaster.class, forVariable(variable), INITS);
    }

    public QConfigurationMaster(Path<? extends ConfigurationMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConfigurationMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConfigurationMaster(PathMetadata metadata, PathInits inits) {
        this(ConfigurationMaster.class, metadata, inits);
    }

    public QConfigurationMaster(Class<? extends ConfigurationMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.authUserProfile = inits.isInitialized("authUserProfile") ? new QAuthUserProfile(forProperty("authUserProfile")) : null;
        this.branchMaster = inits.isInitialized("branchMaster") ? new QBranchMaster(forProperty("branchMaster"), inits.get("branchMaster")) : null;
        this.companyMaster = inits.isInitialized("companyMaster") ? new QCompanyMaster(forProperty("companyMaster"), inits.get("companyMaster")) : null;
        this.groupCompanyMaster = inits.isInitialized("groupCompanyMaster") ? new QGroupCompanyMaster(forProperty("groupCompanyMaster")) : null;
    }

}

