package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDepartmentMaster is a Querydsl query type for DepartmentMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDepartmentMaster extends EntityPathBase<DepartmentMaster> {

    private static final long serialVersionUID = -1289489908L;

    public static final QDepartmentMaster departmentMaster = new QDepartmentMaster("departmentMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath code = createString("code");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final StringPath status = createString("status");

    public final StringPath type = createString("type");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QDepartmentMaster(String variable) {
        super(DepartmentMaster.class, forVariable(variable));
    }

    public QDepartmentMaster(Path<? extends DepartmentMaster> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDepartmentMaster(PathMetadata metadata) {
        super(DepartmentMaster.class, metadata);
    }

}

