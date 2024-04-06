package com.newage.wms.repository;

import com.newage.wms.entity.ConfigurationMaster;
import com.newage.wms.entity.QConfigurationMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationMasterRepository extends JpaRepository<ConfigurationMaster,Long>,
        QuerydslPredicateExecutor<ConfigurationMaster>, QuerydslBinderCustomizer<QConfigurationMaster> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QConfigurationMaster qConfigurationMaster){
        querydslBindings.excludeUnlistedProperties(true);
        querydslBindings.including(qConfigurationMaster.module);
        querydslBindings.including(qConfigurationMaster.value);
        querydslBindings.including(qConfigurationMaster.dataType);
        querydslBindings.including(qConfigurationMaster.status);
        querydslBindings.including(qConfigurationMaster.configurationFlag);
        querydslBindings.including(qConfigurationMaster.tab);
        querydslBindings.including(qConfigurationMaster.branchMaster.id);
        querydslBindings.including(qConfigurationMaster.screen);
        querydslBindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        querydslBindings.bind(qConfigurationMaster.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}