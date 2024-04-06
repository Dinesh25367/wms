package com.newage.wms.repository;

import com.newage.wms.entity.InventoryStatusMaster;
import com.newage.wms.entity.QInventoryStatusMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryStatusMasterRepository extends JpaRepository<InventoryStatusMaster, Long>,
        QuerydslPredicateExecutor<InventoryStatusMaster>, QuerydslBinderCustomizer<QInventoryStatusMaster> {

InventoryStatusMaster getByCode(String code);
    @Override
    default void customize(QuerydslBindings bindings,QInventoryStatusMaster qInventoryStatusMaster){
     bindings.excludeUnlistedProperties(true);
     bindings.including(qInventoryStatusMaster.code);
     bindings.including(qInventoryStatusMaster.branchMaster.id);
     bindings.including(qInventoryStatusMaster.name);
     bindings.including(qInventoryStatusMaster.description);
     bindings.including(qInventoryStatusMaster.isSaleable);
     bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);

    }

}
