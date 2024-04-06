package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHsCodeMaster is a Querydsl query type for HsCodeMaster
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QHsCodeMaster extends EntityPathBase<HsCodeMaster> {

    private static final long serialVersionUID = -1372606510L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHsCodeMaster hsCodeMaster = new QHsCodeMaster("hsCodeMaster");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath code = createString("code");

    public final QCountryMaster country;

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath industry = createString("industry");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final StringPath status = createString("status");

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QHsCodeMaster(String variable) {
        this(HsCodeMaster.class, forVariable(variable), INITS);
    }

    public QHsCodeMaster(Path<? extends HsCodeMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHsCodeMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHsCodeMaster(PathMetadata metadata, PathInits inits) {
        this(HsCodeMaster.class, metadata, inits);
    }

    public QHsCodeMaster(Class<? extends HsCodeMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.country = inits.isInitialized("country") ? new QCountryMaster(forProperty("country"), inits.get("country")) : null;
    }

}

