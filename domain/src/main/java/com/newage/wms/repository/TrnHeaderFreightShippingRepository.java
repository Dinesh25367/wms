package com.newage.wms.repository;

import com.newage.wms.entity.QTrnHeaderFreightShipping;
import com.newage.wms.entity.TrnHeaderFreightShipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface TrnHeaderFreightShippingRepository extends JpaRepository<TrnHeaderFreightShipping,Long>,
        QuerydslPredicateExecutor<TrnHeaderFreightShipping>, QuerydslBinderCustomizer<QTrnHeaderFreightShipping> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QTrnHeaderFreightShipping qTrnHeaderFreightShipping){

    }

}
