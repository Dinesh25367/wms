package com.newage.wms.repository;

import com.newage.wms.entity.QUomMaster;
import com.newage.wms.entity.UomMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface UomMasterRepository extends JpaRepository<UomMaster,Long>,
        QuerydslPredicateExecutor<UomMaster>, QuerydslBinderCustomizer<QUomMaster> {

    UomMaster findByCode(String locationHandlingUomCode);

    @Override
    default void customize(QuerydslBindings bindings, QUomMaster qUomMaster){
        bindings.excludeUnlistedProperties(true);
        bindings.including(qUomMaster.code);
        bindings.including(qUomMaster.name);
        bindings.including(qUomMaster.status);
        bindings.including(qUomMaster.description);
        bindings.including(qUomMaster.restrictionOfUom);
        bindings.including(qUomMaster.reference);
        bindings.including(qUomMaster.categoryMaster.id);
        bindings.including(qUomMaster.categoryMaster.name);
        bindings.including(qUomMaster.categoryMaster.code);
        bindings.including(qUomMaster.branchMaster.id);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(qUomMaster.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
