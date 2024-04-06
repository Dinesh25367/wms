package com.newage.wms.repository;

import com.newage.wms.entity.DoorMaster;
import com.newage.wms.entity.QDoorMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface DoorMasterRepository extends JpaRepository<DoorMaster,Long>,
        QuerydslPredicateExecutor<DoorMaster>, QuerydslBinderCustomizer<QDoorMaster> {

    DoorMaster findByCode(String doorCode);

    @Override
    default void customize(QuerydslBindings querydslBindings, QDoorMaster qDoorMaster){
        querydslBindings.excludeUnlistedProperties(true);
        querydslBindings.including(qDoorMaster.code);
        querydslBindings.including(qDoorMaster.name);
        querydslBindings.including(qDoorMaster.status);
        querydslBindings.including(qDoorMaster.wareHouseMaster.code);
        querydslBindings.including(qDoorMaster.wareHouseMaster.name);
        querydslBindings.including(qDoorMaster.branchMaster.id);
        querydslBindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        querydslBindings.bind(qDoorMaster.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}