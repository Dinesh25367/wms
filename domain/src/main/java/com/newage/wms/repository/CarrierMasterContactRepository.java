package com.newage.wms.repository;

import com.newage.wms.entity.CarrierMasterContact;
import com.newage.wms.entity.QCarrierMasterContact;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrierMasterContactRepository extends JpaRepository<CarrierMasterContact, Long>,
        QuerydslPredicateExecutor<CarrierMasterContact>, QuerydslBinderCustomizer<QCarrierMasterContact> {

    @Query("select cm from CarrierMasterContact cm where cm.branch.id=:branch and cm.agentCode.id=:agentCode")
    CarrierMasterContact findByLineItem ( Long branch, Long agentCode );

    @Override
    default void customize (QuerydslBindings bindings, QCarrierMasterContact root ) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.branch);
        bindings.including(root.branch.id);
        bindings.including(root.agentCode);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
