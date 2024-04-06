package com.newage.wms.repository;

import com.newage.wms.entity.QTrnHeaderCustomsAddlDetails;
import com.newage.wms.entity.TrnHeaderCustomsAddlDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface TrnHeaderCustomsAddlDetailsRepository extends JpaRepository<TrnHeaderCustomsAddlDetails,Long>,
        QuerydslPredicateExecutor<TrnHeaderCustomsAddlDetails>, QuerydslBinderCustomizer<QTrnHeaderCustomsAddlDetails> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QTrnHeaderCustomsAddlDetails qTrnHeaderCustomsAddlDetails){

    }

}
