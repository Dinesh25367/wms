package com.newage.wms.repository;

import com.newage.wms.entity.QSkuLotDetails;
import com.newage.wms.entity.SkuLotDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuLotDetailsRepository extends JpaRepository<SkuLotDetails,Long>,
        QuerydslPredicateExecutor<SkuLotDetails>, QuerydslBinderCustomizer<QSkuLotDetails> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QSkuLotDetails qSkuLotDetails){

    }
}
