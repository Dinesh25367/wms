package com.newage.wms.repository;

import com.newage.wms.entity.QShipmentHeader;
import com.newage.wms.entity.ShipmentHeader;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentHeaderRepository extends JpaRepository<ShipmentHeader, Long>,
        QuerydslPredicateExecutor<ShipmentHeader>, QuerydslBinderCustomizer<QShipmentHeader> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QShipmentHeader qShipmentHeader){
        querydslBindings.excludeUnlistedProperties(true);
        querydslBindings.bind(qShipmentHeader.shipmentUid);
        querydslBindings.bind(qShipmentHeader.status);
        querydslBindings.bind(qShipmentHeader.branchId);
        querydslBindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        querydslBindings.bind(qShipmentHeader.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
