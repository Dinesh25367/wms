package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroupCompanyMaster is a Querydsl query type for GroupCompanyMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGroupCompanyMaster extends EntityPathBase<GroupCompanyMaster> {

    private static final long serialVersionUID = 1322160312L;

    public static final QGroupCompanyMaster groupCompanyMaster = new QGroupCompanyMaster("groupCompanyMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath code = createString("code");

    public final ListPath<CompanyMaster, QCompanyMaster> companyDetails = this.<CompanyMaster, QCompanyMaster>createList("companyDetails", CompanyMaster.class, QCompanyMaster.class, PathInits.DIRECT2);

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final ArrayPath<byte[], Byte> logo = createArray("logo", byte[].class);

    public final StringPath name = createString("name");

    public final StringPath reportingName = createString("reportingName");

    public final StringPath status = createString("status");

    public QGroupCompanyMaster(String variable) {
        super(GroupCompanyMaster.class, forVariable(variable));
    }

    public QGroupCompanyMaster(Path<? extends GroupCompanyMaster> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroupCompanyMaster(PathMetadata metadata) {
        super(GroupCompanyMaster.class, metadata);
    }

}

