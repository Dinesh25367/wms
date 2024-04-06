package com.newage.wms.repository;

import com.newage.wms.entity.QReportMaster;
import com.newage.wms.entity.ReportMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportMasterRepository extends JpaRepository<ReportMaster, Long>,
        QuerydslPredicateExecutor<ReportMaster>, QuerydslBinderCustomizer<QReportMaster> {

    ReportMaster findByReportName(String reportName);

    @Override
    default void customize(QuerydslBindings bindings, QReportMaster root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.id);
        bindings.including(root.screen);
        bindings.including(root.service);
        bindings.including(root.trade);
        bindings.including(root.status);
        bindings.including(root.reportName);
        bindings.including(root.jasperReportTemplate.templateName);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
