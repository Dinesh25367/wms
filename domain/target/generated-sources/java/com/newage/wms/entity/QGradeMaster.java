package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QGradeMaster is a Querydsl query type for GradeMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGradeMaster extends EntityPathBase<GradeMaster> {

    private static final long serialVersionUID = -1398093727L;

    public static final QGradeMaster gradeMaster = new QGradeMaster("gradeMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath colourCode = createString("colourCode");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath priority = createString("priority");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QGradeMaster(String variable) {
        super(GradeMaster.class, forVariable(variable));
    }

    public QGradeMaster(Path<? extends GradeMaster> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGradeMaster(PathMetadata metadata) {
        super(GradeMaster.class, metadata);
    }

}

