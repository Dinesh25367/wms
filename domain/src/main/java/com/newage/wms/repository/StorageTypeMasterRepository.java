package com.newage.wms.repository;

import com.newage.wms.entity.QStorageTypeMaster;
import com.newage.wms.entity.StorageTypeMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageTypeMasterRepository extends JpaRepository<StorageTypeMaster,Long>,
        QuerydslPredicateExecutor<StorageTypeMaster>, QuerydslBinderCustomizer<QStorageTypeMaster> {

    StorageTypeMaster findByCode(String code);

    @Override
    default void customize(QuerydslBindings bindings, QStorageTypeMaster qStorageTypeMaster){
        bindings.excludeUnlistedProperties(true);
        bindings.including(qStorageTypeMaster.code);
        bindings.including(qStorageTypeMaster.name);
        bindings.including(qStorageTypeMaster.status);
        bindings.including(qStorageTypeMaster.branchMaster.id);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(qStorageTypeMaster.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
