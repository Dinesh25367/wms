package com.newage.wms.repository;

import com.newage.wms.entity.AisleMaster;
import com.newage.wms.entity.QAisleMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface AisleMasterRepository extends JpaRepository<AisleMaster,Long>,
        QuerydslPredicateExecutor<AisleMaster>, QuerydslBinderCustomizer<QAisleMaster> {

    AisleMaster findByCode(String code);

    @Override
    default void customize(QuerydslBindings bindings, QAisleMaster qAisleMaster){
        bindings.excludeUnlistedProperties(true);
        bindings.including(qAisleMaster.code);
        bindings.including(qAisleMaster.name);
        bindings.including(qAisleMaster.status);
        bindings.including(qAisleMaster.zoneMaster.id);
        bindings.including(qAisleMaster.branchMaster.id);
        bindings.including(qAisleMaster.zoneMaster.code);
        bindings.including(qAisleMaster.wareHouseMaster.id);
        bindings.including(qAisleMaster.storageAreaMaster.id);
        bindings.including(qAisleMaster.storageTypeMaster.id);
        bindings.including(qAisleMaster.zoneMaster.name);
        bindings.including(qAisleMaster.wareHouseMaster.name);
        bindings.including(qAisleMaster.wareHouseMaster.code);
        bindings.including(qAisleMaster.storageAreaMaster.name);
        bindings.including(qAisleMaster.storageTypeMaster.name);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(qAisleMaster.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
