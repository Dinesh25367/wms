package com.newage.wms.repository;

import com.newage.wms.entity.CustomerMaster;
import com.newage.wms.entity.QCustomerMaster;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

import java.util.List;

public interface CustomerMasterRepository extends JpaRepository<CustomerMaster, Long>,
        QuerydslPredicateExecutor<CustomerMaster>, QuerydslBinderCustomizer<QCustomerMaster> {

    boolean existsByCode(String code);

    CustomerMaster findByCode(String code);

    CustomerMaster findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndCustomerStatus(String name, String customerStatus);

    Page<CustomerMaster> findCustomerApprovalByName(String name, Pageable pageable);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "update CustomerMaster cm set cm.customerStatus =:customerStatus where cm.id =:id")
    int updateApproveStatus(Long id, String customerStatus);

    Page<CustomerMaster> findByCustomerStatusNotIn(List<String> customerStatus, Pageable pageable);

    @Override
    default void customize(QuerydslBindings bindings, QCustomerMaster root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.code);
        bindings.including(root.name);
        bindings.including(root.status);
        bindings.including(root.customerStatus);
        bindings.including(root.country.name);
        bindings.including(root.businessRelationStatus);
        bindings.including(root.branchMaster.id);
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.bind(root.status).first((SingleValueBinding<StringPath, String>) StringExpression::equalsIgnoreCase);
    }

}
