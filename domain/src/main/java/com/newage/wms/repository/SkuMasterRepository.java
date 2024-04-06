package com.newage.wms.repository;

import com.newage.wms.entity.QSkuMaster;
import com.newage.wms.entity.SkuMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuMasterRepository extends JpaRepository<SkuMaster,Long>,
        QuerydslPredicateExecutor<SkuMaster>, QuerydslBinderCustomizer<QSkuMaster> {

    @Override
    default void customize(QuerydslBindings bindings, QSkuMaster qSkuMaster){
        bindings.excludeUnlistedProperties(true);
        bindings.including(qSkuMaster.code);
        bindings.including(qSkuMaster.name);
        bindings.including(qSkuMaster.customerMaster.name);
        bindings.including(qSkuMaster.customerMaster.code);
        bindings.including(qSkuMaster.storageAreaMaster.name);
        bindings.including(qSkuMaster.storageTypeMaster.name);
        bindings.including(qSkuMaster.storageAreaMaster.code);
        bindings.including(qSkuMaster.storageTypeMaster.code);
        bindings.including(qSkuMaster.storageAreaMaster.code);
        bindings.including(qSkuMaster.baseUnitOfMeasurement.code);
        bindings.including(qSkuMaster.baseUnitOfMeasurement.name);
        bindings.including(qSkuMaster.skuGroup);
        bindings.including(qSkuMaster.lovStatus);
        bindings.including(qSkuMaster.branchMaster.id);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(qSkuMaster.lovStatus).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
