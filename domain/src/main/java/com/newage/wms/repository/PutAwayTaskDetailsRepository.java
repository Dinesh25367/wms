package com.newage.wms.repository;

import com.newage.wms.entity.PutAwayTaskDetails;
import com.newage.wms.entity.QPutAwayTaskDetails;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface PutAwayTaskDetailsRepository extends JpaRepository<PutAwayTaskDetails, Long>,
        QuerydslPredicateExecutor<PutAwayTaskDetails>, QuerydslBinderCustomizer<QPutAwayTaskDetails> {

    @Override
    default void customize(QuerydslBindings bindings, QPutAwayTaskDetails qPutAwayTaskDetails){
        bindings.bind(String.class).first((StringPath path, String value) -> {
            return path.startsWithIgnoreCase(value);
        });
        bindings.bind(qPutAwayTaskDetails.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }
}