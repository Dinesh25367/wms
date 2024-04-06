package com.newage.wms.repository;

import com.newage.wms.entity.QSkuPackDetail;
import com.newage.wms.entity.SkuPackDetail;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuPackDetailRepository extends JpaRepository<SkuPackDetail,Long>,
        QuerydslPredicateExecutor<SkuPackDetail>, QuerydslBinderCustomizer<QSkuPackDetail> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QSkuPackDetail qSkuPackDetail){
        querydslBindings.excludeUnlistedProperties(true);
        querydslBindings.including(qSkuPackDetail.uomMaster.code);
        querydslBindings.including(qSkuPackDetail.uomMaster.name);
        querydslBindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
