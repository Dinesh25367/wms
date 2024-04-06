package com.newage.wms.repository;

import com.newage.wms.entity.QRackMaster;
import com.newage.wms.entity.RackMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface RackMasterRepository extends JpaRepository<RackMaster,Long>,
        QuerydslPredicateExecutor<RackMaster>, QuerydslBinderCustomizer<QRackMaster> {

    RackMaster findByCode(String code);

    @Override
    default void customize(QuerydslBindings bindings, QRackMaster qRackMaster){
        bindings.excludeUnlistedProperties(true);
        bindings.including(qRackMaster.code);
        bindings.including(qRackMaster.name);
        bindings.including(qRackMaster.aisleMaster.id);
        bindings.including(qRackMaster.aisleMaster.name);
        bindings.including(qRackMaster.aisleMaster.code);
        bindings.including(qRackMaster.wareHouseMaster.id);
        bindings.including(qRackMaster.wareHouseMaster.name);
        bindings.including(qRackMaster.wareHouseMaster.code);
        bindings.including(qRackMaster.storageAreaMaster.id);
        bindings.including(qRackMaster.storageAreaMaster.name);
        bindings.including(qRackMaster.storageAreaMaster.code);
        bindings.including(qRackMaster.storageTypeMaster.id);
        bindings.including(qRackMaster.storageTypeMaster.name);
        bindings.including(qRackMaster.storageTypeMaster.code);
        bindings.including(qRackMaster.branchMaster.id);
        bindings.including(qRackMaster.status);
        bindings.including(qRackMaster.side);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(qRackMaster.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
