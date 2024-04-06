package com.newage.wms.repository;

import com.newage.wms.entity.QTrnHeaderAddlDetails;
import com.newage.wms.entity.TrnHeaderAddlDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface TrnHeaderAddlDetailsRepository extends JpaRepository<TrnHeaderAddlDetails,Long>,
        QuerydslPredicateExecutor<TrnHeaderAddlDetails>, QuerydslBinderCustomizer<QTrnHeaderAddlDetails> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QTrnHeaderAddlDetails qTrnHeaderAddlDetails){

    }

}
