package com.newage.wms.repository;

import com.newage.wms.entity.AisleMaster;
import com.newage.wms.entity.CategoryMaster;
import com.newage.wms.entity.QCategoryMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface CategoryMasterRepository extends JpaRepository<CategoryMaster,Long>,
        QuerydslPredicateExecutor<CategoryMaster>, QuerydslBinderCustomizer<QCategoryMaster> {

    CategoryMaster findByCode(String code);

    @Override
    default void customize(QuerydslBindings bindings, QCategoryMaster qCategoryMaster){
        bindings.excludeUnlistedProperties(true);
        bindings.including(qCategoryMaster.code);
        bindings.including(qCategoryMaster.branchMaster.id);
        bindings.including(qCategoryMaster.name);
        bindings.including(qCategoryMaster.status);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(qCategoryMaster.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
