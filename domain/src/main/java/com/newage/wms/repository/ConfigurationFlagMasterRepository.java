package com.newage.wms.repository;

import com.newage.wms.entity.ConfigurationFlagMaster;
import com.newage.wms.entity.QConfigurationFlagMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface ConfigurationFlagMasterRepository extends JpaRepository<ConfigurationFlagMaster,Long>,
        QuerydslPredicateExecutor<ConfigurationFlagMaster>, QuerydslBinderCustomizer<QConfigurationFlagMaster> {


    @Override
    default void customize(QuerydslBindings querydslBindings, QConfigurationFlagMaster qConfigurationFlagMaster){
        querydslBindings.excludeUnlistedProperties(true);
        querydslBindings.including(qConfigurationFlagMaster.code);
        querydslBindings.including(qConfigurationFlagMaster.configurationFlag);
        querydslBindings.including(qConfigurationFlagMaster.screen);
        querydslBindings.including(qConfigurationFlagMaster.description);
        querydslBindings.including(qConfigurationFlagMaster.status);
        querydslBindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        querydslBindings.bind(qConfigurationFlagMaster.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }
}
