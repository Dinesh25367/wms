package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAddressTypeMaster is a Querydsl query type for AddressTypeMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAddressTypeMaster extends EntityPathBase<AddressTypeMaster> {

    private static final long serialVersionUID = 1497481880L;

    public static final QAddressTypeMaster addressTypeMaster = new QAddressTypeMaster("addressTypeMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath addressType = createString("addressType");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath status = createString("status");

    public QAddressTypeMaster(String variable) {
        super(AddressTypeMaster.class, forVariable(variable));
    }

    public QAddressTypeMaster(Path<? extends AddressTypeMaster> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddressTypeMaster(PathMetadata metadata) {
        super(AddressTypeMaster.class, metadata);
    }

}

