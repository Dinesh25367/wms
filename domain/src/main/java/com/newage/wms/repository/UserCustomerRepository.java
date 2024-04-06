package com.newage.wms.repository;

import com.newage.wms.entity.UserCustomer;
import com.newage.wms.entity.QUserCustomer;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCustomerRepository extends JpaRepository<UserCustomer, Long>,
        QuerydslPredicateExecutor<UserCustomer>, QuerydslBinderCustomizer<QUserCustomer> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QUserCustomer qUserCustomer){
        querydslBindings.excludeUnlistedProperties(true);
        querydslBindings.including(qUserCustomer.userMaster.userName);
        querydslBindings.including(qUserCustomer.customerMaster.name);
        querydslBindings.including(qUserCustomer.branchMaster.id);
        querydslBindings.including(qUserCustomer.customerMaster.code);
        querydslBindings.including(qUserCustomer.userMaster.id);
        querydslBindings.including(qUserCustomer.status);
        querydslBindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        querydslBindings.bind(qUserCustomer.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
