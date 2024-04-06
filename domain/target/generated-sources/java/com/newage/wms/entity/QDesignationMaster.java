package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDesignationMaster is a Querydsl query type for DesignationMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDesignationMaster extends EntityPathBase<DesignationMaster> {

    private static final long serialVersionUID = 2004335233L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDesignationMaster designationMaster = new QDesignationMaster("designationMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath code = createString("code");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final QDepartmentMaster department;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final StringPath status = createString("status");

    public final StringPath type = createString("type");

    public QDesignationMaster(String variable) {
        this(DesignationMaster.class, forVariable(variable), INITS);
    }

    public QDesignationMaster(Path<? extends DesignationMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDesignationMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDesignationMaster(PathMetadata metadata, PathInits inits) {
        this(DesignationMaster.class, metadata, inits);
    }

    public QDesignationMaster(Class<? extends DesignationMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.department = inits.isInitialized("department") ? new QDepartmentMaster(forProperty("department")) : null;
    }

}

