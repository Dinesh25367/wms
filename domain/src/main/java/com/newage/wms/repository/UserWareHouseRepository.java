package com.newage.wms.repository;

import com.newage.wms.entity.UserWareHouse;
import com.newage.wms.entity.QUserWareHouse;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWareHouseRepository extends JpaRepository<UserWareHouse, Long>,
        QuerydslPredicateExecutor<UserWareHouse>, QuerydslBinderCustomizer<QUserWareHouse> {

    default void customize(QuerydslBindings querydslBindings, QUserWareHouse qUserWareHouse){
        querydslBindings.excludeUnlistedProperties(true);
        querydslBindings.including(qUserWareHouse.wareHouseMaster.name);
        querydslBindings.including(qUserWareHouse.wareHouseMaster.code);
        querydslBindings.including(qUserWareHouse.branchMaster.id);
        querydslBindings.including(qUserWareHouse.userMaster.id);
        querydslBindings.including(qUserWareHouse.userMaster.userName);
        querydslBindings.including(qUserWareHouse.status);
        querydslBindings.including(qUserWareHouse.isPrimary);
        querydslBindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        querydslBindings.bind(qUserWareHouse.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
