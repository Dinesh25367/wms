package com.newage.wms.repository;

import com.newage.wms.entity.QZoneMaster;
import com.newage.wms.entity.ZoneMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import java.util.List;

public interface ZoneMasterRepository extends JpaRepository<ZoneMaster, Long>,
        QuerydslPredicateExecutor<ZoneMaster>, QuerydslBinderCustomizer<QZoneMaster> {

    boolean existsByCode(String code);
    boolean existsByName(String name);
    boolean existsByCountryId(Long countryId);
    List<ZoneMaster> findByCountryId(Long countryId);

    @Query(" select id from ZoneMaster zm where zm.id = (select max(id) from ZoneMaster)\n")
    Long findMaxId();

    @Override
    default void customize(QuerydslBindings bindings, QZoneMaster root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.code);
        bindings.including(root.name);
        bindings.including(root.status);
        bindings.including(root.country.name);
        bindings.including(root.country.code);
        bindings.including(root.country.id);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
