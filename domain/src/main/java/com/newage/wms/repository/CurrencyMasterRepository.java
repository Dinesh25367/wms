package com.newage.wms.repository;

import com.newage.wms.entity.CurrencyMaster;
import com.newage.wms.entity.QCurrencyMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface CurrencyMasterRepository extends JpaRepository<CurrencyMaster, Long>,
        QuerydslPredicateExecutor<CurrencyMaster>, QuerydslBinderCustomizer<QCurrencyMaster> {

    CurrencyMaster getByCode(String code);
    @Override
    default void customize(QuerydslBindings bindings, QCurrencyMaster root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.code);
        bindings.including(root.name);
        bindings.including(root.status);
        bindings.including(root.country.id);
        bindings.including(root.country.name);
        bindings.including(root.country.code);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
