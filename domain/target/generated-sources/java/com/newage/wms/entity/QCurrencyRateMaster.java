package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCurrencyRateMaster is a Querydsl query type for CurrencyRateMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCurrencyRateMaster extends EntityPathBase<CurrencyRateMaster> {

    private static final long serialVersionUID = 1815492107L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCurrencyRateMaster currencyRateMaster = new QCurrencyRateMaster("currencyRateMaster");

    public final QAuditable _super = new QAuditable(this);

    public final QCurrencyMaster accountingCurrency;

    public final NumberPath<Double> buyRate = createNumber("buyRate", Double.class);

    public final QCompanyMaster company;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final DateTimePath<java.util.Date> currencyDate = createDateTime("currencyDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Double> revaluationRate = createNumber("revaluationRate", Double.class);

    public final NumberPath<Double> sellRate = createNumber("sellRate", Double.class);

    public final QCurrencyMaster toCurrency;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QCurrencyRateMaster(String variable) {
        this(CurrencyRateMaster.class, forVariable(variable), INITS);
    }

    public QCurrencyRateMaster(Path<? extends CurrencyRateMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCurrencyRateMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCurrencyRateMaster(PathMetadata metadata, PathInits inits) {
        this(CurrencyRateMaster.class, metadata, inits);
    }

    public QCurrencyRateMaster(Class<? extends CurrencyRateMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.accountingCurrency = inits.isInitialized("accountingCurrency") ? new QCurrencyMaster(forProperty("accountingCurrency"), inits.get("accountingCurrency")) : null;
        this.company = inits.isInitialized("company") ? new QCompanyMaster(forProperty("company"), inits.get("company")) : null;
        this.toCurrency = inits.isInitialized("toCurrency") ? new QCurrencyMaster(forProperty("toCurrency"), inits.get("toCurrency")) : null;
    }

}

