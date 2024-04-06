package com.newage.wms.repository;

import com.newage.wms.entity.ImcoCodeMaster;
import com.newage.wms.entity.QImcoCodeMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface ImcoCodeMasterRepository extends JpaRepository<ImcoCodeMaster,Long>,
        QuerydslPredicateExecutor<ImcoCodeMaster>, QuerydslBinderCustomizer<QImcoCodeMaster> {

    ImcoCodeMaster getByCode(String code);
    @Override
    default void customize(QuerydslBindings bindings, QImcoCodeMaster qImcoCodeMaster){
        bindings.excludeUnlistedProperties(true);
        bindings.including(qImcoCodeMaster.code);
        bindings.including(qImcoCodeMaster.branchMaster.id);
        bindings.including(qImcoCodeMaster.status);
        bindings.including(qImcoCodeMaster.name);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(qImcoCodeMaster.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);

    }

}
