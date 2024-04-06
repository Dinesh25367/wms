package com.newage.wms.repository;

import com.newage.wms.entity.OriginMaster;
import com.newage.wms.entity.QOriginMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface OriginMasterRepository extends JpaRepository<OriginMaster,Long>,
        QuerydslPredicateExecutor<OriginMaster>, QuerydslBinderCustomizer<QOriginMaster> {

    boolean existsByCode(String code);

    boolean existsByName(String name);

    @Override
    default void customize(QuerydslBindings bindings, QOriginMaster root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.code);
        bindings.including(root.name);
        bindings.including(root.status);
        bindings.including(root.transportMode);
        bindings.including(root.countryCode);
        bindings.including(root.country.id);
        bindings.including(root.country.name);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(root.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
