package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWareHouseContactDetail is a Querydsl query type for WareHouseContactDetail
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWareHouseContactDetail extends EntityPathBase<WareHouseContactDetail> {

    private static final long serialVersionUID = -1562850714L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWareHouseContactDetail wareHouseContactDetail = new QWareHouseContactDetail("wareHouseContactDetail");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final ListPath<CustomerContactMaster, QCustomerContactMaster> customerContactMasterList = this.<CustomerContactMaster, QCustomerContactMaster>createList("customerContactMasterList", CustomerContactMaster.class, QCustomerContactMaster.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final QWareHouseMaster wareHouse;

    public QWareHouseContactDetail(String variable) {
        this(WareHouseContactDetail.class, forVariable(variable), INITS);
    }

    public QWareHouseContactDetail(Path<? extends WareHouseContactDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWareHouseContactDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWareHouseContactDetail(PathMetadata metadata, PathInits inits) {
        this(WareHouseContactDetail.class, metadata, inits);
    }

    public QWareHouseContactDetail(Class<? extends WareHouseContactDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.wareHouse = inits.isInitialized("wareHouse") ? new QWareHouseMaster(forProperty("wareHouse"), inits.get("wareHouse")) : null;
    }

}

