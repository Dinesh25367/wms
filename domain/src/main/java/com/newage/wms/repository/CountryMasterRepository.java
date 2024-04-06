package com.newage.wms.repository;

import com.newage.wms.entity.CountryMaster;
import com.newage.wms.entity.QCountryMaster;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface CountryMasterRepository extends JpaRepository<CountryMaster, Long>,
        QuerydslPredicateExecutor<CountryMaster>, QuerydslBinderCustomizer<QCountryMaster> {

    @Override
    default void customize(QuerydslBindings bindings, QCountryMaster qCountryMaster) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(qCountryMaster.name);
        bindings.bind(String.class).first((StringPath path, String value) -> {
            return path.startsWithIgnoreCase(value);
        });
    }

}