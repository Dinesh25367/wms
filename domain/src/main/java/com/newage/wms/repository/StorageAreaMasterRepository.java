package com.newage.wms.repository;

import com.newage.wms.entity.QStorageAreaMaster;
import com.newage.wms.entity.StorageAreaMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageAreaMasterRepository extends JpaRepository<StorageAreaMaster,Long>,
        QuerydslPredicateExecutor<StorageAreaMaster>, QuerydslBinderCustomizer<QStorageAreaMaster> {

    StorageAreaMaster findByCode(String code);

    @Override
    default void customize(QuerydslBindings bindings, QStorageAreaMaster qStorageAreaMaster){
        bindings.excludeUnlistedProperties(true);
        bindings.including(qStorageAreaMaster.code);
        bindings.including(qStorageAreaMaster.name);
        bindings.including(qStorageAreaMaster.status);
        bindings.including(qStorageAreaMaster.branchMaster.id);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(qStorageAreaMaster.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
