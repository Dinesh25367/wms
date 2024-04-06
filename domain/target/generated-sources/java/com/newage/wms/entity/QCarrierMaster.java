package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCarrierMaster is a Querydsl query type for CarrierMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCarrierMaster extends EntityPathBase<CarrierMaster> {

    private static final long serialVersionUID = 2131794114L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCarrierMaster carrierMaster = new QCarrierMaster("carrierMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath carrierPrefix = createString("carrierPrefix");

    public final StringPath code = createString("code");

    public final ListPath<CarrierMasterContact, QCarrierMasterContact> contacts = this.<CarrierMasterContact, QCarrierMasterContact>createList("contacts", CarrierMasterContact.class, QCarrierMasterContact.class, PathInits.DIRECT2);

    public final QCountryMaster country;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ArrayPath<byte[], Byte> image = createArray("image", byte[].class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath localCarrier = createString("localCarrier");

    public final StringPath name = createString("name");

    public final StringPath scacCode = createString("scacCode");

    public final StringPath status = createString("status");

    public final StringPath transportMode = createString("transportMode");

    public QCarrierMaster(String variable) {
        this(CarrierMaster.class, forVariable(variable), INITS);
    }

    public QCarrierMaster(Path<? extends CarrierMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCarrierMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCarrierMaster(PathMetadata metadata, PathInits inits) {
        this(CarrierMaster.class, metadata, inits);
    }

    public QCarrierMaster(Class<? extends CarrierMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.country = inits.isInitialized("country") ? new QCountryMaster(forProperty("country"), inits.get("country")) : null;
    }

}

