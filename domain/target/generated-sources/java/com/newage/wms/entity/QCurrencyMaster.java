package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCurrencyMaster is a Querydsl query type for CurrencyMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCurrencyMaster extends EntityPathBase<CurrencyMaster> {

    private static final long serialVersionUID = 1220731275L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCurrencyMaster currencyMaster = new QCurrencyMaster("currencyMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath code = createString("code");

    public final QCountryMaster country;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath status = createString("status");

    public final StringPath symbol = createString("symbol");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QCurrencyMaster(String variable) {
        this(CurrencyMaster.class, forVariable(variable), INITS);
    }

    public QCurrencyMaster(Path<? extends CurrencyMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCurrencyMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCurrencyMaster(PathMetadata metadata, PathInits inits) {
        this(CurrencyMaster.class, metadata, inits);
    }

    public QCurrencyMaster(Class<? extends CurrencyMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.country = inits.isInitialized("country") ? new QCountryMaster(forProperty("country"), inits.get("country")) : null;
    }

}

