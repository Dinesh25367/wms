package com.newage.wms.repository;

import com.newage.wms.entity.CurrencyRateMaster;
import com.newage.wms.entity.QCurrencyRateMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRateMasterRepository extends JpaRepository<CurrencyRateMaster, Long>,
        QuerydslPredicateExecutor<CurrencyRateMaster>, QuerydslBinderCustomizer<QCurrencyRateMaster> {

    @Override
    default void customize(QuerydslBindings bindings, QCurrencyRateMaster root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.toCurrency.code);
        bindings.including(root.toCurrency.name);
        bindings.including(root.currencyDate);
        bindings.including(root.company.id);
        bindings.including(root.accountingCurrency.id);
        bindings.including(root.toCurrency.id);
        bindings.including(root.sellRate);
        bindings.including(root.buyRate);
        bindings.including(root.revaluationRate);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
