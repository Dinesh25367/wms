package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthUserProfile is a Querydsl query type for AuthUserProfile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthUserProfile extends EntityPathBase<AuthUserProfile> {

    private static final long serialVersionUID = 804464606L;

    public static final QAuthUserProfile authUserProfile = new QAuthUserProfile("authUserProfile");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final StringPath currencyCode = createString("currencyCode");

    public final StringPath employMappingType = createString("employMappingType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath organisationAccess = createString("organisationAccess");

    public final NumberPath<Long> primaryBranchEmployeeId = createNumber("primaryBranchEmployeeId", Long.class);

    public final StringPath primaryBranchEmployeeName = createString("primaryBranchEmployeeName");

    public final NumberPath<Long> primaryCompanyId = createNumber("primaryCompanyId", Long.class);

    public final StringPath primaryCompanyName = createString("primaryCompanyName");

    public final StringPath primaryEmailId = createString("primaryEmailId");

    public final NumberPath<Long> primaryLoginBranchId = createNumber("primaryLoginBranchId", Long.class);

    public final StringPath primaryLoginBranchName = createString("primaryLoginBranchName");

    public final NumberPath<Long> primaryRoleId = createNumber("primaryRoleId", Long.class);

    public final StringPath status = createString("status");

    public final StringPath userName = createString("userName");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QAuthUserProfile(String variable) {
        super(AuthUserProfile.class, forVariable(variable));
    }

    public QAuthUserProfile(Path<? extends AuthUserProfile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthUserProfile(PathMetadata metadata) {
        super(AuthUserProfile.class, metadata);
    }

}

