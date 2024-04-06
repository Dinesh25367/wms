package com.newage.wms.repository;

import com.newage.wms.entity.Location;
import com.newage.wms.entity.QLocation;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long>,
        QuerydslPredicateExecutor<Location>, QuerydslBinderCustomizer<QLocation> {

    @Override
    default void customize(QuerydslBindings bindings, QLocation qLocation){
        bindings.excludeUnlistedProperties(true);
        bindings.including(qLocation.locationUid);
        bindings.including(qLocation.abc);
        bindings.including(qLocation.checkDigit);
        bindings.including(qLocation.columnCode);
        bindings.including(qLocation.levelCode);
        bindings.including(qLocation.position);
        bindings.including(qLocation.wareHouseMaster.name);
        bindings.including(qLocation.wareHouseMaster.id);
        bindings.including(qLocation.wareHouseMaster.code);
        bindings.including(qLocation.zoneMaster.name);
        bindings.including(qLocation.aisleMaster.name);
        bindings.including(qLocation.rackMaster.name);
        bindings.including(qLocation.storageAreaMaster.name);
        bindings.including(qLocation.storageTypeMaster.name);
        bindings.including(qLocation.locTypeMaster.name);
        bindings.including(qLocation.locationHandlingUomMaster.name);
        bindings.including(qLocation.branchMaster.id);
        bindings.including(qLocation.status);
        bindings.including(qLocation.id);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(qLocation.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
