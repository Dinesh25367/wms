package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRegionMaster is a Querydsl query type for RegionMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRegionMaster extends EntityPathBase<RegionMaster> {

    private static final long serialVersionUID = 169652270L;

    public static final QRegionMaster regionMaster = new QRegionMaster("regionMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath code = createString("code");

    public final ListPath<CountryMaster, QCountryMaster> countries = this.<CountryMaster, QCountryMaster>createList("countries", CountryMaster.class, QCountryMaster.class, PathInits.DIRECT2);

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath status = createString("status");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QRegionMaster(String variable) {
        super(RegionMaster.class, forVariable(variable));
    }

    public QRegionMaster(Path<? extends RegionMaster> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRegionMaster(PathMetadata metadata) {
        super(RegionMaster.class, metadata);
    }

}

