package com.newage.wms.repository;

import com.newage.wms.entity.QWareHouseMaster;
import com.newage.wms.entity.WareHouseMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouseMaster,Long>,
        QuerydslPredicateExecutor<WareHouseMaster>, QuerydslBinderCustomizer<QWareHouseMaster> {

    WareHouseMaster findByCode(String code);

    @Override
    default void customize(QuerydslBindings bindings, QWareHouseMaster qWareHouse){
        bindings.excludeUnlistedProperties(true);
        bindings.including(qWareHouse.code);
        bindings.including(qWareHouse.name);
        bindings.including(qWareHouse.status);
        bindings.including(qWareHouse.branchMaster.id);
        bindings.including(qWareHouse.wareHouseLocationPrefix);
        bindings.including(qWareHouse.isBonded);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(qWareHouse.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}