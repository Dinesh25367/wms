package com.newage.wms.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTrnHeaderCustomsDocument is a Querydsl query type for TrnHeaderCustomsDocument
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTrnHeaderCustomsDocument extends EntityPathBase<TrnHeaderCustomsDocument> {

    private static final long serialVersionUID = -1055735304L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTrnHeaderCustomsDocument trnHeaderCustomsDocument = new QTrnHeaderCustomsDocument("trnHeaderCustomsDocument");

    public final QAuditable _super = new QAuditable(this);

    public final StringPath accepted_tolerance = createString("accepted_tolerance");

    public final StringPath createdBy = createString("createdBy");

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final DateTimePath<java.util.Date> docDate = createDateTime("docDate", java.util.Date.class);

    public final QCustomerMaster docPassedCompanyMaster;

    public final StringPath docPassedPerson = createString("docPassedPerson");

    public final StringPath docRef1 = createString("docRef1");

    public final StringPath docRef2 = createString("docRef2");

    public final StringPath documentType = createString("documentType");

    public final StringPath documentValue = createString("documentValue");

    public final StringPath ftaReferenceNo = createString("ftaReferenceNo");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QCustomerMaster iorMaster;

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final QTrnHeaderAsn trnHeaderAsn;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QTrnHeaderCustomsDocument(String variable) {
        this(TrnHeaderCustomsDocument.class, forVariable(variable), INITS);
    }

    public QTrnHeaderCustomsDocument(Path<? extends TrnHeaderCustomsDocument> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTrnHeaderCustomsDocument(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTrnHeaderCustomsDocument(PathMetadata metadata, PathInits inits) {
        this(TrnHeaderCustomsDocument.class, metadata, inits);
    }

    public QTrnHeaderCustomsDocument(Class<? extends TrnHeaderCustomsDocument> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.docPassedCompanyMaster = inits.isInitialized("docPassedCompanyMaster") ? new QCustomerMaster(forProperty("docPassedCompanyMaster"), inits.get("docPassedCompanyMaster")) : null;
        this.iorMaster = inits.isInitialized("iorMaster") ? new QCustomerMaster(forProperty("iorMaster"), inits.get("iorMaster")) : null;
        this.trnHeaderAsn = inits.isInitialized("trnHeaderAsn") ? new QTrnHeaderAsn(forProperty("trnHeaderAsn"), inits.get("trnHeaderAsn")) : null;
    }

}

