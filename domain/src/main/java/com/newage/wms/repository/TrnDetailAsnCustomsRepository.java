package com.newage.wms.repository;

import com.newage.wms.entity.QTrnDetailAsnCustoms;
import com.newage.wms.entity.TrnDetailAsnCustoms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface TrnDetailAsnCustomsRepository extends JpaRepository<TrnDetailAsnCustoms,Long>,
        QuerydslPredicateExecutor<TrnDetailAsnCustoms>, QuerydslBinderCustomizer<QTrnDetailAsnCustoms> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QTrnDetailAsnCustoms qTrnDetailAsnCustoms){

    }

}
