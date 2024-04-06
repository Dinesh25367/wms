package com.newage.wms.repository;

import com.newage.wms.entity.QTrnHeaderAsn;
import com.newage.wms.entity.TrnHeaderAsn;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface TrnHeaderAsnRepository extends JpaRepository<TrnHeaderAsn,Long>,
        QuerydslPredicateExecutor<TrnHeaderAsn>, QuerydslBinderCustomizer<QTrnHeaderAsn> {

    @Override
    default void customize(QuerydslBindings bindings, QTrnHeaderAsn qTrnHeaderAsn){
        bindings.bind(String.class).first((StringPath path, String value) -> {
            return path.containsIgnoreCase(value);
        });
        bindings.bind(qTrnHeaderAsn.transactionStatus).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}