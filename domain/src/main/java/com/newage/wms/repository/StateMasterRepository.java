package com.newage.wms.repository;

import com.newage.wms.entity.QStateMaster;
import com.newage.wms.entity.StateMaster;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import java.util.Optional;

public interface StateMasterRepository extends JpaRepository<StateMaster, Long>,
        QuerydslPredicateExecutor<StateMaster>, QuerydslBinderCustomizer<QStateMaster> {

    @Override
    @EntityGraph(attributePaths = {"country", "country.currency"})
    Page<StateMaster> findAll(Predicate var1, Pageable var2);

    @Override
    @EntityGraph(attributePaths = {"country", "country.currency"})
    Optional<StateMaster> findById(Long id);

    @Override
    default void customize(QuerydslBindings bindings, QStateMaster root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.code);
        bindings.including(root.name);
        bindings.including(root.status);
        bindings.including(root.country.code);
        bindings.including(root.country.id);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
