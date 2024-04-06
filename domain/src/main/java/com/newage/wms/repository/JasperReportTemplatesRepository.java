package com.newage.wms.repository;

import com.newage.wms.entity.JasperReportTemplates;
import com.newage.wms.entity.QJasperReportTemplates;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface JasperReportTemplatesRepository extends JpaRepository<JasperReportTemplates, Long>,
        QuerydslPredicateExecutor<JasperReportTemplates>, QuerydslBinderCustomizer<QJasperReportTemplates> {


    @Override
    default void customize(QuerydslBindings bindings, QJasperReportTemplates root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.id);
        bindings.including(root.reportCategory);
        bindings.including(root.templateName);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}

