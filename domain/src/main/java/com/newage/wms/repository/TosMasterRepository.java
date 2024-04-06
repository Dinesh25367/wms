package com.newage.wms.repository;

import com.newage.wms.entity.QTosMaster;
import com.newage.wms.entity.TosMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface TosMasterRepository extends JpaRepository<TosMaster, Long>,
        QuerydslPredicateExecutor<TosMaster>, QuerydslBinderCustomizer<QTosMaster> {

    boolean existsByCode(String code);

    boolean existsByName(String name);

    @Override
    default void customize(QuerydslBindings bindings, QTosMaster root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.freightPPCC);
        bindings.including(root.status);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(root.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
