package com.newage.wms.repository;

import com.newage.wms.entity.CompanyMaster;
import com.newage.wms.entity.QCompanyMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import java.util.List;
import java.util.Optional;

public interface CompanyMasterRepository extends JpaRepository<CompanyMaster, Long>,
        QuerydslPredicateExecutor<CompanyMaster>, QuerydslBinderCustomizer<QCompanyMaster> {

    boolean existsByCode(String code);
    boolean existsByName(String name);
    boolean existsByReportingName(String reportingName);
    Optional<List<CompanyMaster>> findByGroupCompanyId (Long groupCompanyMasterId);

    @Override
    default void customize(QuerydslBindings bindings, QCompanyMaster root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.code);
        bindings.including(root.name);
        bindings.including(root.reportingName);
        bindings.including(root.status);
        bindings.including(root.groupCompany.code);
        bindings.including(root.groupCompany.name);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}

