package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomerTypeMaster is a Querydsl query type for CustomerTypeMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerTypeMaster extends EntityPathBase<CustomerTypeMaster> {

    private static final long serialVersionUID = 306233458L;

    public static final QCustomerTypeMaster customerTypeMaster = new QCustomerTypeMaster("customerTypeMaster");

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

    public final StringPath status = createString("status");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QCustomerTypeMaster(String variable) {
        super(CustomerTypeMaster.class, forVariable(variable));
    }

    public QCustomerTypeMaster(Path<? extends CustomerTypeMaster> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomerTypeMaster(PathMetadata metadata) {
        super(CustomerTypeMaster.class, metadata);
    }

}

