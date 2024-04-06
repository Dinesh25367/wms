package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCountryMaster is a Querydsl query type for CountryMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCountryMaster extends EntityPathBase<CountryMaster> {

    private static final long serialVersionUID = -268721440L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCountryMaster countryMaster = new QCountryMaster("countryMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath capital = createString("capital");

    public final SetPath<CityMaster, QCityMaster> cities = this.<CityMaster, QCityMaster>createSet("cities", CityMaster.class, QCityMaster.class, PathInits.DIRECT2);

    public final StringPath code = createString("code");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final QCurrencyMaster currency;

    public final StringPath currencyCode = createString("currencyCode");

    public final StringPath flag = createString("flag");

    public final StringPath flagUnicode = createString("flagUnicode");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath iso3 = createString("iso3");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath nativeName = createString("nativeName");

    public final StringPath phoneCode = createString("phoneCode");

    public final QRegionMaster region;

    public final SetPath<StateMaster, QStateMaster> states = this.<StateMaster, QStateMaster>createSet("states", StateMaster.class, QStateMaster.class, PathInits.DIRECT2);

    public final StringPath status = createString("status");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QCountryMaster(String variable) {
        this(CountryMaster.class, forVariable(variable), INITS);
    }

    public QCountryMaster(Path<? extends CountryMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCountryMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCountryMaster(PathMetadata metadata, PathInits inits) {
        this(CountryMaster.class, metadata, inits);
    }

    public QCountryMaster(Class<? extends CountryMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.currency = inits.isInitialized("currency") ? new QCurrencyMaster(forProperty("currency"), inits.get("currency")) : null;
        this.region = inits.isInitialized("region") ? new QRegionMaster(forProperty("region")) : null;
    }

}

