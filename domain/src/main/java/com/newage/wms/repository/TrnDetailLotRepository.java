package com.newage.wms.repository;

import com.newage.wms.entity.QTrnDetailLot;
import com.newage.wms.entity.TrnDetailLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface TrnDetailLotRepository extends JpaRepository<TrnDetailLot,Long>,
        QuerydslPredicateExecutor<TrnDetailLot>, QuerydslBinderCustomizer<QTrnDetailLot> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QTrnDetailLot qTrnDetailLot){

    }

}
