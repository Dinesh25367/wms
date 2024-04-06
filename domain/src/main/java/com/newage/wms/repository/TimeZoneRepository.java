package com.newage.wms.repository;

import com.newage.wms.entity.QTimeZoneMaster;
import com.newage.wms.entity.TimeZoneMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface TimeZoneRepository extends JpaRepository<TimeZoneMaster, Long>,
        QuerydslPredicateExecutor<TimeZoneMaster>, QuerydslBinderCustomizer<QTimeZoneMaster> {

    @Override
    default void customize(QuerydslBindings bindings, QTimeZoneMaster root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.abbreviation);
        bindings.including(root.countryCode);
        bindings.including(root.zoneName);
        bindings.including(root.status);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
