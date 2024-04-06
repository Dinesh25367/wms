package com.newage.wms.repository;

import com.newage.wms.entity.MovementTypeMaster;
import com.newage.wms.entity.QMovementTypeMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementTypeMasterRepository extends JpaRepository<MovementTypeMaster,Long>,
        QuerydslPredicateExecutor<MovementTypeMaster>, QuerydslBinderCustomizer<QMovementTypeMaster> {

    MovementTypeMaster findByCode(String code);

    @Override
    default void customize(QuerydslBindings bindings, QMovementTypeMaster qMovementTypeMaster){
        bindings.excludeUnlistedProperties(true);
        bindings.including(qMovementTypeMaster.code);
        bindings.including(qMovementTypeMaster.branchMaster.id);
        bindings.including(qMovementTypeMaster.status);
        bindings.including(qMovementTypeMaster.warehouseInOut);
        bindings.including(qMovementTypeMaster.description);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(qMovementTypeMaster.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
