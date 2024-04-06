package com.newage.wms.repository;

import com.newage.wms.entity.LocTypeMaster;
import com.newage.wms.entity.QLocTypeMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface LocTypeMasterRepository extends JpaRepository<LocTypeMaster,Long>,
        QuerydslPredicateExecutor<LocTypeMaster>, QuerydslBinderCustomizer<QLocTypeMaster> {

    LocTypeMaster findByCode(String code);

    @Override
    default void customize(QuerydslBindings bindings, QLocTypeMaster qLocTypeMaster){
        bindings.excludeUnlistedProperties(true);
        bindings.including(qLocTypeMaster.code);
        bindings.including(qLocTypeMaster.name);
        bindings.including(qLocTypeMaster.status);
        bindings.including(qLocTypeMaster.type);
        bindings.including(qLocTypeMaster.branchMaster.id);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(qLocTypeMaster.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
