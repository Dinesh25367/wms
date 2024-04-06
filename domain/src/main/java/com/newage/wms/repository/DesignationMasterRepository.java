package com.newage.wms.repository;

import com.newage.wms.entity.DesignationMaster;
import com.newage.wms.entity.QDesignationMaster;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface DesignationMasterRepository extends JpaRepository<DesignationMaster, Long>,
        QuerydslPredicateExecutor<DesignationMaster>, QuerydslBinderCustomizer<QDesignationMaster> {

    boolean existsByName(String name);
    boolean existsByCode(String code);

    @Override
    default void customize(QuerydslBindings bindings, QDesignationMaster qDesignationMaster) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(qDesignationMaster.name);
        bindings.including(qDesignationMaster.code);
        bindings.bind(String.class).first((StringPath path, String value) -> {
            return path.startsWithIgnoreCase(value);
        });
    }

}
