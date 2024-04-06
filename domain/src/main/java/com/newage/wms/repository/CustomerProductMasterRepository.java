package com.newage.wms.repository;

import com.newage.wms.entity.CustomerProductMaster;
import com.newage.wms.entity.QCustomerProductMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface CustomerProductMasterRepository extends JpaRepository<CustomerProductMaster, Long>,
        QuerydslPredicateExecutor<CustomerProductMaster>, QuerydslBinderCustomizer<QCustomerProductMaster> {

    @Override
    default void customize(QuerydslBindings bindings, QCustomerProductMaster root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.id);
        bindings.including(root.origin);
        bindings.including(root.destination);
        bindings.including(root.noOfShipment);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
