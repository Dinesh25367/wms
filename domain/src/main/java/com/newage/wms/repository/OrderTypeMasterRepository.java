package com.newage.wms.repository;

import com.newage.wms.entity.OrderTypeMaster;
import com.newage.wms.entity.QOrderTypeMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTypeMasterRepository extends JpaRepository<OrderTypeMaster,Long >,
        QuerydslPredicateExecutor<OrderTypeMaster>, QuerydslBinderCustomizer<QOrderTypeMaster> {

    @Override
    default void customize(QuerydslBindings bindings, QOrderTypeMaster qOrderTypeMaster){
        bindings.excludeUnlistedProperties(true);
        bindings.including(qOrderTypeMaster.code);
        bindings.including(qOrderTypeMaster.branchMaster.id);
        bindings.including(qOrderTypeMaster.status);
        bindings.including(qOrderTypeMaster.warehouseInOut);
        bindings.including(qOrderTypeMaster.description);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(qOrderTypeMaster.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
