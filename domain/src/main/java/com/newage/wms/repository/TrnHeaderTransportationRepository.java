package com.newage.wms.repository;

import com.newage.wms.entity.QTrnHeaderTransportation;
import com.newage.wms.entity.TrnHeaderTransportation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface TrnHeaderTransportationRepository extends JpaRepository<TrnHeaderTransportation,Long>,
        QuerydslPredicateExecutor<TrnHeaderTransportation>, QuerydslBinderCustomizer<QTrnHeaderTransportation> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QTrnHeaderTransportation qTrnHeaderTransportation){

    }

}
