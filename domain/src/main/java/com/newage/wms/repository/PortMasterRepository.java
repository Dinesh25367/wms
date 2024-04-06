package com.newage.wms.repository;

import com.newage.wms.entity.PortMaster;
import com.newage.wms.entity.QPortMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface PortMasterRepository extends JpaRepository<PortMaster, Long>,
        QuerydslPredicateExecutor<PortMaster>, QuerydslBinderCustomizer<QPortMaster> {

    boolean existsByCode(String code);
    boolean existsByName(String name);

    @Override
    default void customize(QuerydslBindings bindings, QPortMaster root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.code);
        bindings.including(root.name);
        bindings.including(root.status);
        bindings.including(root.transportMode);
        bindings.including(root.countryCode);
        bindings.including(root.country.id);
        bindings.including(root.country.name);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
