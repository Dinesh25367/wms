package com.newage.wms.repository;

import com.newage.wms.entity.CarrierMaster;
import com.newage.wms.entity.QCarrierMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrierMasterRepository extends JpaRepository<CarrierMaster,Long>,
        QuerydslPredicateExecutor<CarrierMaster>, QuerydslBinderCustomizer<QCarrierMaster> {

    boolean existsByCode ( String code );

    boolean existsByName ( String name );

    boolean existsByCarrierPrefix(String carrierPrefix);

    @Override
    default void customize ( QuerydslBindings bindings, QCarrierMaster root ) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.code);
        bindings.including(root.name);
        bindings.including(root.transportMode);
        bindings.including(root.country.name);
        bindings.including(root.status);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(root.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
