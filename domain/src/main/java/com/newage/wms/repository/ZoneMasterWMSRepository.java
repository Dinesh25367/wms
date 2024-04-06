package com.newage.wms.repository;

import com.newage.wms.entity.QZoneMasterWMS;
import com.newage.wms.entity.ZoneMasterWMS;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneMasterWMSRepository extends JpaRepository<ZoneMasterWMS,Long>,
        QuerydslPredicateExecutor<ZoneMasterWMS>, QuerydslBinderCustomizer<QZoneMasterWMS> {

    ZoneMasterWMS findByCode(String code);

    @Override
    default void customize(QuerydslBindings bindings, QZoneMasterWMS qZoneMasterWMS){
        bindings.including(qZoneMasterWMS.code);
        bindings.including(qZoneMasterWMS.name);
        bindings.including(qZoneMasterWMS.branchMaster.id);
        bindings.including(qZoneMasterWMS.wareHouseMaster.id);
        bindings.including(qZoneMasterWMS.wareHouseMaster.name);
        bindings.including(qZoneMasterWMS.wareHouseMaster.code);
        bindings.including(qZoneMasterWMS.storageAreaMaster.id);
        bindings.including(qZoneMasterWMS.storageAreaMaster.name);
        bindings.including(qZoneMasterWMS.storageAreaMaster.code);
        bindings.including(qZoneMasterWMS.storageTypeMaster.id);
        bindings.including(qZoneMasterWMS.storageTypeMaster.code);
        bindings.including(qZoneMasterWMS.storageTypeMaster.name);
        bindings.including(qZoneMasterWMS.inLocationMaster.id);
        bindings.including(qZoneMasterWMS.inLocationMaster.locationUid);
        bindings.including(qZoneMasterWMS.outLocationMaster.id);
        bindings.including(qZoneMasterWMS.outLocationMaster.locationUid);
        bindings.including(qZoneMasterWMS.doorMaster.id);
        bindings.including(qZoneMasterWMS.doorMaster.code);
        bindings.including(qZoneMasterWMS.doorMaster.name);
        bindings.including(qZoneMasterWMS.status);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(qZoneMasterWMS.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
