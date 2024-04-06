package com.newage.wms.repository;

import com.newage.wms.entity.CustomerConfigurationMaster;
import com.newage.wms.entity.QCustomerConfigurationMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerConfigurationMasterRepository extends JpaRepository<CustomerConfigurationMaster, Long>,
        QuerydslPredicateExecutor<CustomerConfigurationMaster>, QuerydslBinderCustomizer<QCustomerConfigurationMaster> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QCustomerConfigurationMaster qCustomerConfigurationMaster){
        querydslBindings.excludeUnlistedProperties(true);
        querydslBindings.including(qCustomerConfigurationMaster.module);
        querydslBindings.including(qCustomerConfigurationMaster.branchMaster.id);
        querydslBindings.including(qCustomerConfigurationMaster.customerMaster.id);
        querydslBindings.including(qCustomerConfigurationMaster.dataType);
        querydslBindings.including(qCustomerConfigurationMaster.customerMaster.name);
        querydslBindings.including(qCustomerConfigurationMaster.configurationFlag);
        querydslBindings.including(qCustomerConfigurationMaster.isMandatory);
        querydslBindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);


    }

}
