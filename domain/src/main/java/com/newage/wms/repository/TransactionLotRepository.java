package com.newage.wms.repository;

import com.newage.wms.entity.QTransactionLot;
import com.newage.wms.entity.TransactionLot;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLotRepository extends JpaRepository<TransactionLot,Long>,
        QuerydslPredicateExecutor<TransactionLot>, QuerydslBinderCustomizer<QTransactionLot> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QTransactionLot qTransactionLot){
        querydslBindings.bind(String.class).first((StringPath path, String value) -> {
            return path.startsWithIgnoreCase(value);
        });
    }

}

