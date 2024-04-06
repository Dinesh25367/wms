package com.newage.wms.repository;

import com.newage.wms.entity.QQcStatusMaster;
import com.newage.wms.entity.QcStatusMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface QcStatusMasterRepository extends JpaRepository<QcStatusMaster,Long>,
        QuerydslPredicateExecutor<QcStatusMaster>, QuerydslBinderCustomizer<QQcStatusMaster> {

    @Override
    default void customize(QuerydslBindings bindings, QQcStatusMaster qQcStatusMaster) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(qQcStatusMaster.code);
        bindings.including(qQcStatusMaster.name);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}