package com.newage.wms.repository;

import com.newage.wms.entity.QVesselMaster;
import com.newage.wms.entity.VesselMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface VesselMasterRepository extends JpaRepository<VesselMaster, Long>,
        QuerydslPredicateExecutor<VesselMaster>, QuerydslBinderCustomizer<QVesselMaster> {

    boolean existsByName (String name);

    boolean existsByShortName (String shortName);

    boolean existsByImoNumber (String imoNumber);

    boolean existsByCallSign (String callSign);

    @Override
    default void customize(QuerydslBindings bindings, QVesselMaster root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.name);
        bindings.including(root.shortName);
        bindings.including(root.vesselCountry.name);
        bindings.including(root.callSign);
        bindings.including(root.imoNumber);
        bindings.including(root.status);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(root.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}

