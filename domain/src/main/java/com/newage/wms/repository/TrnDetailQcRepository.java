package com.newage.wms.repository;

import com.newage.wms.entity.QTrnDetailQc;
import com.newage.wms.entity.TrnDetailQc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface TrnDetailQcRepository extends JpaRepository<TrnDetailQc,Long>,
        QuerydslPredicateExecutor<TrnDetailQc>, QuerydslBinderCustomizer<QTrnDetailQc> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QTrnDetailQc qTrnDetailQc){

    }

}
