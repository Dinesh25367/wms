package com.newage.wms.repository;

import com.newage.wms.entity.QTrnHeaderSo;
import com.newage.wms.entity.TrnHeaderSo;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository

public interface TrnHeaderSoRepository extends JpaRepository<TrnHeaderSo,Long>,
        QuerydslPredicateExecutor<TrnHeaderSo>, QuerydslBinderCustomizer<QTrnHeaderSo> {

    @Override
    default void customize(QuerydslBindings bindings, QTrnHeaderSo qTrnHeaderSo){
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
