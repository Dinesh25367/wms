package com.newage.wms.repository;

import com.newage.wms.entity.GroupCompanyMaster;
import com.newage.wms.entity.QGroupCompanyMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface GroupCompanyMasterRepository extends JpaRepository<GroupCompanyMaster, Long>,
        QuerydslPredicateExecutor<GroupCompanyMaster>, QuerydslBinderCustomizer<QGroupCompanyMaster> {

    boolean existsByCode(String code);
    boolean existsByName(String name);
    boolean existsByReportingName(String reportingName);

    @Override
    default void customize(QuerydslBindings bindings, QGroupCompanyMaster root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.code);
        bindings.including(root.name);
        bindings.including(root.reportingName);
        bindings.including(root.status);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}