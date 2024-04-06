package com.newage.wms.repository;

import com.newage.wms.entity.QTrnHeaderParty;
import com.newage.wms.entity.TrnHeaderParty;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface TrnHeaderPartyRepository extends JpaRepository<TrnHeaderParty,Long> ,
        QuerydslPredicateExecutor<TrnHeaderParty>, QuerydslBinderCustomizer<QTrnHeaderParty> {

    @Override
    default void customize(QuerydslBindings bindings, QTrnHeaderParty qTrnHeaderParty){
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
