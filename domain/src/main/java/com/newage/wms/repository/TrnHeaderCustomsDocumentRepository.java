package com.newage.wms.repository;

import com.newage.wms.entity.QTrnHeaderCustomsDocument;
import com.newage.wms.entity.TrnHeaderCustomsDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface TrnHeaderCustomsDocumentRepository extends JpaRepository<TrnHeaderCustomsDocument,Long>,
        QuerydslPredicateExecutor<TrnHeaderCustomsDocument>, QuerydslBinderCustomizer<QTrnHeaderCustomsDocument> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QTrnHeaderCustomsDocument qTrnHeaderCustomsDocument){

    }

}
